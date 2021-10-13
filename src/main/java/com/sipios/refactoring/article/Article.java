package com.sipios.refactoring.article;

public abstract class Article {
    double seasonal_discount;
    double price;

    public Article(double seasonal_discount, double price) {
        this.seasonal_discount = seasonal_discount;
        this.price = price;
    }

    public double getSeasonal_discount() {
        return seasonal_discount;
    }

    public double getPrice() {
        return price;
    }
}
