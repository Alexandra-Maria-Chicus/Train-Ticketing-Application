package com.trainticket.config;

import com.trainticket.domain.*;
import com.trainticket.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StationRepository stationRepository;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public DataSeeder(StationRepository stationRepository, TrainRepository trainRepository,
                      RouteRepository routeRepository, RouteStopRepository routeStopRepository,
                      ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.stationRepository = stationRepository;
        this.trainRepository = trainRepository;
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Station cluj = new Station("Cluj-Napoca");
        Station sinaia = new Station("Sinaia");
        Station brasov = new Station("Brasov");
        Station ploiesti = new Station("Ploiesti");
        Station bucuresti = new Station("Bucuresti");
        stationRepository.save(cluj);
        stationRepository.save(sinaia);
        stationRepository.save(brasov);
        stationRepository.save(ploiesti);
        stationRepository.save(bucuresti);

        Train intercity = new Train("InterCity 100", 200);
        Train regional = new Train("Regional 200", 150);
        trainRepository.save(intercity);
        trainRepository.save(regional);

        Route route1 = new Route("Cluj - Bucuresti");
        routeRepository.save(route1);
        routeStopRepository.save(new RouteStop(route1, cluj, 0, 0, 0));
        routeStopRepository.save(new RouteStop(route1, brasov, 1, 180, 185));
        routeStopRepository.save(new RouteStop(route1, bucuresti, 2, 360, 360));

        Route route2 = new Route("Bucuresti - Brasov");
        routeRepository.save(route2);
        routeStopRepository.save(new RouteStop(route2, bucuresti, 0, 0, 0));
        routeStopRepository.save(new RouteStop(route2, ploiesti, 1, 60, 65));
        routeStopRepository.save(new RouteStop(route2, sinaia, 2, 120, 125));
        routeStopRepository.save(new RouteStop(route2, brasov, 3, 180, 180));

        Schedule s1 = new Schedule(intercity, route1, LocalDateTime.of(2026, 6, 1, 8, 0));
        Schedule s2 = new Schedule(intercity, route1, LocalDateTime.of(2026, 6, 1, 14, 0));
        Schedule s3 = new Schedule(regional, route2, LocalDateTime.of(2026, 6, 1, 9, 0));
        scheduleRepository.save(s1);
        scheduleRepository.save(s2);
        scheduleRepository.save(s3);

        User admin = new User("Admin", Role.ADMIN, "admin@trainticket.com", "admin123");
        User customer = new User("John Doe", Role.CUSTOMER, "john@example.com", "customer123");
        userRepository.save(admin);
        userRepository.save(customer);
    }
}