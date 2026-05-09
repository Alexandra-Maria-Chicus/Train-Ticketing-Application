package com.trainticket.service;

import com.trainticket.domain.*;
import com.trainticket.exception.NoRouteFoundException;
import com.trainticket.exception.OverbookingException;
import com.trainticket.exception.ValidationException;
import com.trainticket.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final StationRepository stationRepository;
    private final UserRepository userRepository;
    private BookingRepository bookingRepository;
    private ScheduleRepository scheduleRepository;
    private EmailService emailService;
    private RouteStopRepository routeStopRepository;

    public BookingService(BookingRepository bookingRepository, ScheduleRepository scheduleRepository, EmailService emailService, RouteStopRepository routeStopRepository, StationRepository stationRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.scheduleRepository = scheduleRepository;
        this.emailService = emailService;
        this.routeStopRepository = routeStopRepository;
        this.stationRepository = stationRepository;
        this.userRepository = userRepository;
    }

    public void bookTicket(long userId, long fromStationId, long toStationId, long scheduleId, int seatCount) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ValidationException("Schedule does not exist"));
        Station fromStation = stationRepository.findById(fromStationId).orElseThrow(() -> new ValidationException("From Station does not exist"));
        Station toStation = stationRepository.findById(toStationId).orElseThrow(() -> new ValidationException("To Station does not exist"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ValidationException("User does not exist"));
        List<RouteStop> stops = routeStopRepository.findByRoute(schedule.getRoute());

        RouteStop from = stops.stream()
                .filter(s -> s.getStation().getId() == fromStation.getId())
                .findFirst()
                .orElseThrow(() -> new NoRouteFoundException("From station not on this route"));

        RouteStop to = stops.stream()
                .filter(s -> s.getStation().getId() == toStation.getId())
                .findFirst()
                .orElseThrow(() -> new NoRouteFoundException("To station not on this route"));

        if (from.getStopOrder() >= to.getStopOrder()) {
            throw new NoRouteFoundException("Invalid direction");
        }

        List<Booking> bookings = bookingRepository.findBySchedule(schedule);
        int seatsOccupied = 0;
        for (Booking b : bookings) {
            seatsOccupied += b.getSeatCount();
        }
        if (seatCount + seatsOccupied > schedule.getTrain().getCapacity()) {
            throw new OverbookingException();
        }
        Booking b = new Booking(user, fromStation, toStation, schedule, seatCount, LocalDateTime.now());
        bookingRepository.save(b);
        emailService.sendBookingConfirmation(user.getEmail(), user.getName(), fromStation.getName(), toStation.getName(), schedule.getDepartureTime(), seatCount);
    }

    public List<List<Schedule>> findTravelOptions(long fromStationId, long toStationId) {
        Station fromStation = stationRepository.findById(fromStationId)
                .orElseThrow(() -> new ValidationException("From Station does not exist"));
        Station toStation = stationRepository.findById(toStationId)
                .orElseThrow(() -> new ValidationException("To Station does not exist"));

        List<Schedule> schedules = scheduleRepository.findAll();
        List<List<Schedule>> output = new ArrayList<>();
        for (Schedule sch : schedules) {
            List<RouteStop> stops = routeStopRepository.findByRoute(sch.getRoute());
            RouteStop from = stops.stream()
                    .filter(s -> s.getStation().getId() == fromStation.getId())
                    .findFirst().orElse(null);
            RouteStop to = stops.stream()
                    .filter(s -> s.getStation().getId() == toStation.getId())
                    .findFirst().orElse(null);
            if (from != null && to != null && from.getStopOrder() < to.getStopOrder()) {
                output.add(List.of(sch));
            }
        }
        for (Schedule firstLeg : schedules) {
            List<RouteStop> firstStops = routeStopRepository.findByRoute(firstLeg.getRoute());
            RouteStop fromStop = firstStops.stream()
                    .filter(s -> s.getStation().getId() == fromStation.getId())
                    .findFirst().orElse(null);
            if (fromStop == null) continue;
            List<RouteStop> intermediateStops = firstStops.stream()
                    .filter(s -> s.getStopOrder() > fromStop.getStopOrder())
                    .toList();

            for (RouteStop intermediate : intermediateStops) {
                for (Schedule secondLeg : schedules) {
                    if (secondLeg.getId() == firstLeg.getId()) continue;
                    if (secondLeg.getRoute().getId() == firstLeg.getRoute().getId()) continue;

                    List<RouteStop> secondStops = routeStopRepository.findByRoute(secondLeg.getRoute());
                    RouteStop intermediateStop = secondStops.stream()
                            .filter(s -> s.getStation().getId() == intermediate.getStation().getId())
                            .findFirst().orElse(null);
                    RouteStop toStop = secondStops.stream()
                            .filter(s -> s.getStation().getId() == toStation.getId())
                            .findFirst().orElse(null);

                    if (intermediateStop != null && toStop != null
                            && intermediateStop.getStopOrder() < toStop.getStopOrder()) {
                        boolean alreadyAdded = output.stream()
                                .anyMatch(option -> option.size() == 2 &&
                                        option.contains(firstLeg) && option.contains(secondLeg));
                        if (!alreadyAdded) {
                            output.add(List.of(firstLeg, secondLeg));
                        }
                    }
                }
            }
        }

        if (output.isEmpty())
            throw new NoRouteFoundException("No routes found between these stations!");
        return output;
    }
}