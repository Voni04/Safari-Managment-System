package com.safari.safari_trip_system.designpatterns.observer;

import com.safari.safari_trip_system.entity.Booking;
import java.util.ArrayList;
import java.util.List;

public class BookingSubject {
    private List<BookingObserver> observers = new ArrayList<>();

    public void attach(BookingObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.update(booking);
        }
    }
}