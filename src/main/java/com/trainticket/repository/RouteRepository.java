package com.trainticket.repository;

import com.trainticket.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route,Long> {
    public Route findByName(String name);
}
