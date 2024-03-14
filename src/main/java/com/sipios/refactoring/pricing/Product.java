package com.sipios.refactoring.pricing;

import java.util.Optional;

public enum Product {
    TSHIRT(30),
    DRESS(50),
    JACKET(100);

    private final int price;

    Product(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public static Optional<Product> findByName(String name) {
        try {
            return Optional.of(valueOf(name));
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }
}
