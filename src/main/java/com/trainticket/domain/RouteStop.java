package com.trainticket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class RouteStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;
    private int stopOrder;
    private int arrivalOffset;
    private int departureOffset;

    public RouteStop(Route route, Station station, int stopOrder, int arrivalOffset, int departureOffset) {
        this.route = route;
        this.station = station;
        this.stopOrder = stopOrder;
        this.arrivalOffset = arrivalOffset;
        this.departureOffset = departureOffset;
    }

    public RouteStop(){}

    public void setId(long id) {
        this.id = id;
    }

    public void setStopOrder(int stopOrder) {
        this.stopOrder = stopOrder;
    }

    public void setArrivalOffset(int arrivalOffset) {
        this.arrivalOffset = arrivalOffset;
    }

    public void setDepartureOffset(int departureOffset) {
        this.departureOffset = departureOffset;
    }

    public long getId() {
        return id;
    }

    public int getStopOrder() {
        return stopOrder;
    }

    public int getArrivalOffset() {
        return arrivalOffset;
    }

    public int getDepartureOffset() {
        return departureOffset;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
