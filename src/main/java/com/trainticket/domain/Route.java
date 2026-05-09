package com.trainticket.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
    private List<RouteStop> stops = new ArrayList<>();

    public Route( String name, List<RouteStop> stops) {
        this.name = name;
        this.stops = stops;
    }
    public Route(){}

    public Route(String name){
        this.name=name;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RouteStop> getStops() {
        return stops;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStops(List<RouteStop> stops) {
        this.stops = stops;
    }
}
