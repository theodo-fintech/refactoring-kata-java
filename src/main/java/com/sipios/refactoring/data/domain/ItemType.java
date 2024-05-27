package com.sipios.refactoring.data.domain;

/**
 * A {@code ItemType} is...
 * TODO: write javadoc...
 */
public enum ItemType {

    TSHIRT (30,  1  ),
    DRESS  (50,  0.8),
    JACKET (100, 0.9);

    private final double price;
    private final double seasonalDiscount;

    ItemType(double price, double seasonalDiscount) {
        this.price = price;
        this.seasonalDiscount = seasonalDiscount;
    }

    public double getPrice() {
        return price;
    }

    public double getSeasonalDiscount() {
        return seasonalDiscount;
    }

}
