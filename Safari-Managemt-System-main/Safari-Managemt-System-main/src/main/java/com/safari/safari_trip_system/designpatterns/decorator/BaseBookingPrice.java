package com.safari.safari_trip_system.designpatterns.decorator;

public class BaseBookingPrice implements PriceComponent {
    private double basePrice;

    public BaseBookingPrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public double getCost() {
        return basePrice;
    }
}