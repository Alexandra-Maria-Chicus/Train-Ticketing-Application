package com.trainticket.dto;

public class RouteStopRequest {
    private long stationId;
    private int arrivalOffset;
    private int departureOffset;

    public RouteStopRequest(long stationId, int arrivalOffset, int departureOffset) {
        this.stationId = stationId;
        this.arrivalOffset = arrivalOffset;
        this.departureOffset = departureOffset;
    }

    public long getStationId() {
        return stationId;
    }

    public int getArrivalOffset() {
        return arrivalOffset;
    }

    public int getDepartureOffset() {
        return departureOffset;
    }
}
