package com.trainticket.service;

import com.trainticket.domain.*;
import com.trainticket.dto.RouteStopRequest;
import com.trainticket.exception.ValidationException;
import com.trainticket.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    private RouteRepository routeRepository;
    private RouteStopRepository routeStopRepository;
    private TrainRepository trainRepository;
    private BookingRepository bookingRepository;
    private StationRepository stationRepository;
    private ScheduleRepository scheduleRepository;
    private EmailService emailService;

    public AdminService(RouteRepository routeRepository, RouteStopRepository routeStopRepository, TrainRepository trainRepository, BookingRepository bookingRepository, StationRepository stationRepository, ScheduleRepository scheduleRepository, EmailService emailService) {
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
        this.trainRepository = trainRepository;
        this.bookingRepository = bookingRepository;
        this.stationRepository = stationRepository;
        this.scheduleRepository = scheduleRepository;
        this.emailService = emailService;
    }

    public void addTrain(String name, int capacity){
        if(name.isEmpty())
            throw new ValidationException("Provide a train name!");
        if(capacity<=0 || capacity>=5000)
            throw  new ValidationException("Capacity number not valid!");
        Train t=new Train(name, capacity);
        trainRepository.save(t);
    }
    public void updateTrain(long trainId, String name, int capacity){
        Train t=trainRepository.findById(trainId).orElse(null);
        if(t==null)
            throw new ValidationException("Train does not exist");
        if(name.isEmpty())
            throw new ValidationException("Provide a train name!");
        if(capacity<=0 || capacity>=5000)
            throw  new ValidationException("Capacity number not valid!");
        t.setCapacity(capacity);
        t.setName(name);
        trainRepository.save(t);
    }
    public void deleteTrain(long trainId){
        trainRepository.deleteById(trainId);
    }

    public List<Booking> getBookingsForTrain(long trainId){
        Train train= trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found"));
        return bookingRepository.findBySchedule_Train(train);
    }
    public void addRoute(String name, List<RouteStopRequest> stops) {
        if (name.isEmpty())
            throw new ValidationException("Provide a route name!");
        if (stops.size() < 2)
            throw new ValidationException("A route must have at least 2 stations!");

        Route route = new Route(name);
        routeRepository.save(route);

        for (int i = 0; i < stops.size(); i++) {
            RouteStopRequest req = stops.get(i);
            Station station = stationRepository.findById(req.getStationId())
                    .orElseThrow(() -> new ValidationException("Station not found"));
            RouteStop stop = new RouteStop(route, station, i, req.getArrivalOffset(), req.getDepartureOffset());
            routeStopRepository.save(stop);
        }
    }
    public void updateRoute(long routeId, String name, List<RouteStopRequest> stops) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ValidationException("Route not found"));
        if (name.isEmpty())
            throw new ValidationException("Provide a route name!");
        if (stops.size() < 2)
            throw new ValidationException("A route must have at least 2 stations!");

        route.setName(name);
        routeStopRepository.deleteRouteStopsByRoute(route);

        for (int i = 0; i < stops.size(); i++) {
            RouteStopRequest req = stops.get(i);
            Station station = stationRepository.findById(req.getStationId())
                    .orElseThrow(() -> new ValidationException("Station not found"));
            RouteStop stop = new RouteStop(route, station, i, req.getArrivalOffset(), req.getDepartureOffset());
            routeStopRepository.save(stop);
        }
        routeRepository.save(route);
    }
    public void deleteRoute(long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ValidationException("Route not found"));
        routeStopRepository.deleteRouteStopsByRoute(route);
        routeRepository.delete(route);
    }

    public void addSchedule(long trainId, long routeId, LocalDateTime departure){
        Train t=trainRepository.findById(trainId).orElseThrow(()->new ValidationException("Train not found"));
        Route r= routeRepository.findById(routeId).orElseThrow(()-> new ValidationException("Route not found"));
        Schedule schedule= new Schedule(t,r,departure);
        scheduleRepository.save(schedule);
    }

    public void updateSchedule(long scheduleId, long trainId, long routeId, LocalDateTime departure){
        Schedule s= scheduleRepository.findById(scheduleId).orElseThrow(()->new ValidationException("Schedule not found"));
        Train t=trainRepository.findById(trainId).orElseThrow(()->new ValidationException("Train not found"));
        Route r= routeRepository.findById(routeId).orElseThrow(()-> new ValidationException("Route not found"));
        s.setTrain(t);
        s.setRoute(r);
        s.setDepartureTime(departure);
        scheduleRepository.save(s);
    }

    public void deleteSchedule(long scheduleId){
        Schedule s= scheduleRepository.findById(scheduleId).orElseThrow(()->new ValidationException("Schedule not found"));
        scheduleRepository.delete(s);
    }

    public void addStation(String name){
        if(name.isEmpty())
            throw  new ValidationException("Name cannot be empty");
        Station station= new Station(name);
        stationRepository.save(station);
    }

    public void updateStation(long stationId,String name){
        if(name.isEmpty())
            throw  new ValidationException("Name cannot be empty");
        Station station= stationRepository.findById(stationId).orElseThrow(()->new ValidationException("Station not found"));
        station.setName(name);
        stationRepository.save(station);
    }

    public void deleteStation(long stationId){
        Station station= stationRepository.findById(stationId).orElseThrow(()->new ValidationException("Station not found"));
        stationRepository.delete(station);
    }

    public void setDelay(long scheduleId, int delayMinutes) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ValidationException("Schedule not found"));
        schedule.setDelayMinutes(delayMinutes);
        scheduleRepository.save(schedule);

        List<Booking> bookings = bookingRepository.findBySchedule(schedule);
        for (Booking b : bookings) {
            try{
                emailService.sendDelayNotification(
                    b.getUser().getEmail(),
                    b.getUser().getName(),
                    delayMinutes
                );
            }
            catch (Exception e){
                System.out.println("Email failed: " + e.getMessage());
            }
        }
    }
}
