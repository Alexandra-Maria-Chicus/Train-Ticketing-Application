package com.trainticket.dto;

public class BookingRequest {
    private long userId;
    private long fromStationId;
    private long toStationId;
    private long scheduleId;
    private int seatCount;

    public BookingRequest(long userId, long fromStationId, long toStationId, long scheduleId, int seatCount) {
        this.userId = userId;
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.scheduleId = scheduleId;
        this.seatCount = seatCount;
    }

    public BookingRequest(){}

    public long getUserId() {
        return userId;
    }

    public long getFromStationId() {
        return fromStationId;
    }

    public long getToStationId() {
        return toStationId;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public int getSeatCount() {
        return seatCount;
    }
}
