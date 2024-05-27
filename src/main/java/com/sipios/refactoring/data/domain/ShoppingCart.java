package com.sipios.refactoring.data.domain;

import java.util.List;

/**
 * A {@code ShoppingCart} is...
 * TODO: write javadoc...
 */
public class ShoppingCart {

    private final List<Item> items;
    private final CustomerType customerType;

    public ShoppingCart(List<Item> items, CustomerType customerType) {
        this.items = items;
        this.customerType = customerType;
    }

    public List<Item> getItems() {
        return items;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

}
