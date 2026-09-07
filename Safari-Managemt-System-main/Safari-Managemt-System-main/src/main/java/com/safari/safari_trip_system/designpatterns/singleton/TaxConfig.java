package com.safari.safari_trip_system.designpatterns.singleton;

public class TaxConfig {

    // 1. Static variable to hold the single instance
    private static TaxConfig instance;

    // 2. Global setting
    private double taxRate;

    // 3. Private constructor: No one else can create this object!
    private TaxConfig() {
        this.taxRate = 0.05; // Default 5% tax
    }

    // 4. Public static method to get the single instance
    public static synchronized TaxConfig getInstance() {
        if (instance == null) {
            instance = new TaxConfig();
        }
        return instance;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
}
