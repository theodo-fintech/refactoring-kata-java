package com.sipios.refactoring.pricing;

public enum CustomerPlan {

    STANDARD_CUSTOMER("standard", 1, 200),
    PREMIUM_CUSTOMER("premium", 0.9, 800),
    PLATINUM_CUSTOMER("platinum", 0.5, 2000);

    private final String label;
    private final double discount;
    private final int totalPriceThreshold;

    CustomerPlan(String label, double discount, int totalPriceThreshold) {
        this.label = label;
        this.discount = discount;
        this.totalPriceThreshold = totalPriceThreshold;
    }

    public String getLabel() {
        return label;
    }

    public double getDiscount() {
        return discount;
    }

    public int getTotalPriceThreshold() {
        return totalPriceThreshold;
    }
}
