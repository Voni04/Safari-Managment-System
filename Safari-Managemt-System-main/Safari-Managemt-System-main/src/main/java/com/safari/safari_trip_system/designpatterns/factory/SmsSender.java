package com.safari.safari_trip_system.designpatterns.factory;

import com.safari.safari_trip_system.entity.User;

public class SmsSender implements NotificationSender {
    @Override
    public void send(User user, String message) {
        System.out.println("📱 [FACTORY SMS] To: " + user.getFirstName() + " | Msg: " + message);
    }
}
