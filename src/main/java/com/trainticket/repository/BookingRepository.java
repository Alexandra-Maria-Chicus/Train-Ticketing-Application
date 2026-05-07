package com.trainticket.repository;

import com.trainticket.domain.Booking;
import com.trainticket.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    public List<Booking> findBySchedule(Schedule schedule);
}
