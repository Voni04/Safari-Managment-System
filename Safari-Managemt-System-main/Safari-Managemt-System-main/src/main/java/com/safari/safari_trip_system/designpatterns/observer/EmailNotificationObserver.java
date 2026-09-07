package com.safari.safari_trip_system.designpatterns.observer;

import com.safari.safari_trip_system.designpatterns.factory.NotificationFactory;
import com.safari.safari_trip_system.designpatterns.factory.NotificationSender;
import com.safari.safari_trip_system.entity.Booking;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailNotificationObserver implements BookingObserver {

    private final JavaMailSender mailSender;

    // We accept the MailSender in the constructor
    public EmailNotificationObserver(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void update(Booking booking) {
        // Pass the mailSender to the Factory
        NotificationSender emailer = NotificationFactory.createSender("EMAIL", mailSender);

        String subject = "Booking Confirmed!";
        String body = "Dear " + booking.getUser().getFirstName() + ",\n\n" +
                "Your booking for '" + booking.getSafariPackage().getPackageName() + "' is CONFIRMED.\n" +
                "Date: " + booking.getBookingDate() + "\n\n" +
                "Thank you for choosing Safari System!";

        emailer.send(booking.getUser(), body);
    }
}