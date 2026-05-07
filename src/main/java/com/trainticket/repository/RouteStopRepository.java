package com.trainticket.repository;

import com.trainticket.domain.Route;
import com.trainticket.domain.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteStopRepository extends JpaRepository<RouteStop,Long> {
    public List<RouteStop> findByRoute(Route route);
    public void deleteRouteStopsByRoute(Route route);
}
