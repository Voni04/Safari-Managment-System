package com.safari.safari_trip_system.service;

import com.safari.safari_trip_system.entity.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // Simulate sending an Email
    public void sendBookingConfirmationEmail(User user, String packageName, Double amount) {
        System.out.println("==================================================");
        System.out.println("📧 [EMAIL SIMULATION] Sending to: " + user.getEmail());
        System.out.println("Subject: Booking Confirmed - " + packageName);
        System.out.println("Body: Dear " + user.getFirstName() + ",");
        System.out.println("Your payment of $" + amount + " was successful.");
        System.out.println("Your trip '" + packageName + "' is now CONFIRMED.");
        System.out.println("Thank you for choosing Safari System!");
        System.out.println("==================================================");
    }

    // Simulate sending an SMS
    public void sendBookingConfirmationSms(User user) {
        System.out.println("📱 [SMS SIMULATION] Sending to " + user.getFirstName() + "'s Mobile...");
        System.out.println("Message: Safari System Alert: Your trip is confirmed! Check your email for details.");
        System.out.println("==================================================");
    }
}
