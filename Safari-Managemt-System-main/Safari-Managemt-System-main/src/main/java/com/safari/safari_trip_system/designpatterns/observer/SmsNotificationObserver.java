package com.safari.safari_trip_system.designpatterns.observer;

import com.safari.safari_trip_system.designpatterns.factory.NotificationFactory;
import com.safari.safari_trip_system.designpatterns.factory.NotificationSender;
import com.safari.safari_trip_system.entity.Booking;

public class SmsNotificationObserver implements BookingObserver {
    @Override
    public void update(Booking booking) {
        // We pass 'null' because SmsSender doesn't need the Mail tool
        NotificationSender smser = NotificationFactory.createSender("SMS", null);
        smser.send(booking.getUser(), "Booking " + booking.getId() + " Confirmed.");
    }
}
