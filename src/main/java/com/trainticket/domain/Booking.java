package com.trainticket.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "from_station_id")
    private Station fromStation;
    @ManyToOne
    @JoinColumn(name = "to_station_id")
    private Station toStation;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    private int seatCount;
    private LocalDateTime bookingTime;

    public Booking(User user, Station fromStation, Station toStation, Schedule schedule, int seatCount, LocalDateTime bookingTime) {
        this.user = user;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.schedule = schedule;
        this.seatCount = seatCount;
        this.bookingTime = bookingTime;
    }
    public Booking(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Station getToStation() {
        return toStation;
    }

    public void setToStation(Station toStation) {
        this.toStation = toStation;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public void setFromStation(Station fromStation) {
        this.fromStation = fromStation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
