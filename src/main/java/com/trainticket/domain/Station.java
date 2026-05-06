package com.trainticket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Station {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;

    public Station(String name) {
        this.name = name;
    }
    public Station() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
