package com.trainticket.repository;

import com.trainticket.domain.Route;
import com.trainticket.domain.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteStopRepository extends JpaRepository<RouteStop,Long> {
    public RouteStop findByRoute(Route route);
}
