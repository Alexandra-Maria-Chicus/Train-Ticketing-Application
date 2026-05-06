package com.trainticket.repository;

import com.trainticket.domain.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, Long> {
    public Train findByName(String name);
}
