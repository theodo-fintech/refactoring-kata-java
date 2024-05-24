package com.sipios.refactoring.data.domain;

public enum CustomerType {

    STANDARD(1,    800),
    PREMIUM (0.9, 2000),
    PLATINUM(0.5,  200);

    private final double discount;
    private final double priceLimit;

    CustomerType(double discount, double priceLimit) {
        this.discount = discount;
        this.priceLimit = priceLimit;
    }

    public double getDiscount() {
        return discount;
    }

    public double getPriceLimit() {
        return priceLimit;
    }

    public boolean accepts(double price) {
        return (price <= priceLimit);
    }

}
