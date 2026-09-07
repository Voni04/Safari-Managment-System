package com.safari.safari_trip_system.designpatterns.decorator;

public abstract class BookingDecorator implements PriceComponent {
    protected PriceComponent tempBooking;

    public BookingDecorator(PriceComponent newBooking) {
        this.tempBooking = newBooking;
    }

    @Override
    public double getCost() {
        return tempBooking.getCost();
    }
}