package com.trainticket.exception;

public class OverbookingException extends RuntimeException {
    public OverbookingException() {
        super("Not enough available seats!");
    }
}
