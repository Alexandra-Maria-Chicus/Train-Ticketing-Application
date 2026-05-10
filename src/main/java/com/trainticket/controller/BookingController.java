package com.trainticket.controller;

import com.trainticket.domain.Booking;
import com.trainticket.domain.Schedule;
import com.trainticket.dto.BookingRequest;
import com.trainticket.service.AdminService;
import com.trainticket.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public void bookTicket(@RequestBody BookingRequest request){
        bookingService.bookTicket(request.getUserId(),request.getFromStationId(),request.getToStationId(),request.getScheduleId(), request.getSeatCount());
    }

    @GetMapping("/search")
    public List<List<Schedule>> findTravelOptions(@RequestParam long fromStationId, @RequestParam long toStationId){
        return bookingService.findTravelOptions(fromStationId,toStationId);
    }
}
