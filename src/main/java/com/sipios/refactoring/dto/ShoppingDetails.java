package com.sipios.refactoring.dto;

import com.sipios.refactoring.utils.enums.CustomerType;

public class ShoppingDetails {

    private ShoppingItem[] items;
    private CustomerType type;

    public ShoppingDetails(ShoppingItem[] items, CustomerType type) {
        this.items = items;
        this.type = type;
    }

    public ShoppingDetails() {}

    public ShoppingItem[] getItems() {
        return items;
    }

    public void setItems(ShoppingItem[] items) {
        this.items = items;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }
}
