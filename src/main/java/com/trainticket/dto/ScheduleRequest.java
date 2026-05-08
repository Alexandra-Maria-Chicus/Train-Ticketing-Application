package com.trainticket.dto;

import java.time.LocalDateTime;

public class ScheduleRequest {
    private long trainId;
    private long routeId;
    private LocalDateTime departureTime;

    public ScheduleRequest(long trainId, long routeId, LocalDateTime departureTime) {
        this.trainId = trainId;
        this.routeId = routeId;
        this.departureTime = departureTime;
    }

    public long getTrainId() {
        return trainId;
    }

    public long getRouteId() {
        return routeId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
}
