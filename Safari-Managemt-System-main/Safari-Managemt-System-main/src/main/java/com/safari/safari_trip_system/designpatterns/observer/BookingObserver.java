package com.safari.safari_trip_system.designpatterns.observer;

import com.safari.safari_trip_system.entity.Booking;

public interface BookingObserver {
    void update(Booking booking);
}
