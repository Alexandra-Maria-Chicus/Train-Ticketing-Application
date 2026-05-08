package com.trainticket.controller;
import com.trainticket.domain.Booking;
import com.trainticket.dto.RouteRequest;
import com.trainticket.dto.ScheduleRequest;
import com.trainticket.dto.StationRequest;
import com.trainticket.dto.TrainRequest;
import com.trainticket.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/trains")
    public void addTrain(@RequestBody TrainRequest request) {
        adminService.addTrain(request.getName(), request.getCapacity());
    }

    @PutMapping("/trains/{id}")
    public void updateTrain(@PathVariable long id, @RequestBody TrainRequest request) {
        adminService.updateTrain(id, request.getName(), request.getCapacity());
    }

    @DeleteMapping("/trains/{id}")
    public void deleteTrain(@PathVariable long id) {
        adminService.deleteTrain(id);
    }

    @GetMapping("/trains/{id}/bookings")
    public List<Booking> getBookingsForTrain(@PathVariable long id) {
        return adminService.getBookingsForTrain(id);
    }

    @PostMapping("/stations")
    public void addStation(@RequestBody StationRequest request) {
        adminService.addStation(request.getName());
    }

    @PutMapping("/stations/{id}")
    public void updateStation(@PathVariable long id, @RequestBody StationRequest request) {
        adminService.updateStation(id, request.getName());
    }

    @DeleteMapping("/stations/{id}")
    public void deleteStation(@PathVariable long id) {
        adminService.deleteStation(id);
    }

    @PostMapping("/routes")
    public void addRoute(@RequestBody RouteRequest request) {
        adminService.addRoute(request.getName(), request.getStops());
    }

    @PutMapping("/routes/{id}")
    public void updateRoute(@PathVariable long id, @RequestBody RouteRequest request) {
        adminService.updateRoute(id, request.getName(), request.getStops());
    }

    @DeleteMapping("/routes/{id}")
    public void deleteRoute(@PathVariable long id) {
        adminService.deleteRoute(id);
    }

    @PostMapping("/schedules")
    public void addSchedule(@RequestBody ScheduleRequest request) {
        adminService.addSchedule(request.getTrainId(), request.getRouteId(), request.getDepartureTime());
    }

    @PutMapping("/schedules/{id}")
    public void updateSchedule(@PathVariable long id, @RequestBody ScheduleRequest request) {
        adminService.updateSchedule(id, request.getTrainId(), request.getRouteId(), request.getDepartureTime());
    }

    @DeleteMapping("/schedules/{id}")
    public void deleteSchedule(@PathVariable long id) {
        adminService.deleteSchedule(id);
    }

    @PutMapping("/schedules/{id}/delay")
    public void setDelay(@PathVariable long id, @RequestParam int delayMinutes) {
        adminService.setDelay(id, delayMinutes);
    }
}
