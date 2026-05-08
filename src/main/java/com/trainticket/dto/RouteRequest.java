package com.trainticket.dto;

import java.util.List;

public class RouteRequest {
    private String name;
    private List<RouteStopRequest> stops;

    public RouteRequest(String name, List<RouteStopRequest> stops) {
        this.name = name;
        this.stops = stops;
    }

    public String getName() {
        return name;
    }

    public List<RouteStopRequest> getStops() {
        return stops;
    }
}
