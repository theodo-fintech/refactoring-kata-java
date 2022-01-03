package com.sipios.refactoring.model;

public class ShoppingCart {

    private final ItemQuantity[] items;
    private final CustomerType customerType;

    public ShoppingCart(ItemQuantity[] is, CustomerType t) {
        this.items = is;
        this.customerType = t;
    }

    public ItemQuantity[] getItems() {
        return items;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }
}
