package com.safari.safari_trip_system.designpatterns.factory;

import com.safari.safari_trip_system.entity.User;

public interface NotificationSender {
    void send(User user, String message);
}