package com.trainticket.repository;

import com.trainticket.domain.Booking;
import com.trainticket.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    public Booking findBySchedule(Schedule schedule);
}
