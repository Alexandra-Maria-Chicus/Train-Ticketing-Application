package com.trainticket.service;

import com.trainticket.domain.Booking;
import com.trainticket.domain.Schedule;
import com.trainticket.domain.User;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    MailSender mailSender;

    public EmailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendBookingConfirmation(String toEmail, String customerName,
                                        String fromStation, String toStation,
                                        LocalDateTime departureTime, int seats) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Booking Confirmation");
        message.setText("Dear " + customerName + ",\n\n" +
                "Your booking has been confirmed!\n" +
                "From: " + fromStation + "\n" +
                "To: " + toStation + "\n" +
                "Departure: " + departureTime + "\n" +
                "Seats: " + seats + "\n\n" +
                "Thank you for choosing our service!");
        mailSender.send(message);
    }

    public void sendDelayNotification(String toEmail, String customerName, int delayMinutes){
        SimpleMailMessage message= new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Delay Notification");
        message.setText("Dear "+customerName+",\n"+
                "Your train has a delay of "+delayMinutes+" minutes.\n"+
                "We are sorry for any inconvenience created!");
        mailSender.send(message);
    }
}
