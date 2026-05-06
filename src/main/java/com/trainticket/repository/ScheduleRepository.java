package com.trainticket.repository;

import com.trainticket.domain.Route;
import com.trainticket.domain.Schedule;
import com.trainticket.domain.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    public Schedule findByRoute(Route route);
    public Schedule findByTrain(Train train);
}
