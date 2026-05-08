package com.trainticket.dto;

public class TrainRequest {
    private String name;
    private int capacity;

    public TrainRequest(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
