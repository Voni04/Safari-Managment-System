package com.safari.safari_trip_system.designpatterns.factory;

import com.safari.safari_trip_system.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailSender implements NotificationSender {

    private final JavaMailSender javaMailSender;

    // We pass the mail tool via constructor
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(User user, String message) {
        try {
            System.out.println("📧 Connecting to Gmail...");

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getEmail());
            mail.setSubject("Safari Trip Update");
            mail.setText(message);

            // This actually sends the email over the internet
            javaMailSender.send(mail);

            System.out.println("✅ Email successfully sent to " + user.getEmail());
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}