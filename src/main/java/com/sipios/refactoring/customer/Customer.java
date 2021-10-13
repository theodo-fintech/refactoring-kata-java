package com.sipios.refactoring.customer;

public abstract class Customer {
    private double discount;
    private int threshold;

    public Customer(double discount, int threshold) {
        this.discount = discount;
        this.threshold = threshold;
    }

    public double getDiscount() {
        return discount;
    }

    public int getThreshold() {
        return threshold;
    }
}
