package com.trainticket.repository;

import com.trainticket.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
    public Station findByName(String name);
}
