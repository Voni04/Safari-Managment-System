package com.safari.safari_trip_system.designpatterns.decorator;

public class WithTaxDecorator extends BookingDecorator {
    private double taxRate;

    public WithTaxDecorator(PriceComponent newBooking, double taxRate) {
        super(newBooking); // Passes the booking up to the parent
        this.taxRate = taxRate;
    }

    @Override
    public double getCost() {
        // Calculate: Base Cost + (Base Cost * Tax Rate)
        return super.getCost() + (super.getCost() * taxRate);
    }
}
