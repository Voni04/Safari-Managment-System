package com.safari.safari_trip_system.designpatterns.factory;

import org.springframework.mail.javamail.JavaMailSender;

public class NotificationFactory {

    // We added JavaMailSender as a parameter so we can pass it to EmailSender
    public static NotificationSender createSender(String type, JavaMailSender mailSender) {
        if (type.equalsIgnoreCase("EMAIL")) {
            return new EmailSender(mailSender);
        } else if (type.equalsIgnoreCase("SMS")) {
            return new SmsSender();
        } else {
            throw new IllegalArgumentException("Unknown notification type");
        }
    }
}