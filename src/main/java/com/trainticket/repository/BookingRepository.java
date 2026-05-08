package com.trainticket.repository;

import com.trainticket.domain.Booking;
import com.trainticket.domain.Schedule;
import com.trainticket.domain.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    public List<Booking> findBySchedule(Schedule schedule);
    public List<Booking> findBySchedule_Train(Train train);
}
