package com.sipios.refactoring.entity;

public class Price {
    private double basePrice;
    private double percentageDiscount;

    public Price(double price, double percentageDiscount) {
        this.basePrice = price;
        this.percentageDiscount = percentageDiscount;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(double percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }
}
