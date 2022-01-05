package com.sipios.refactoring.dto;

import com.sipios.refactoring.dto.ShoppingItem;

public class ShoppingDetails {

    private ShoppingItem[] items;
    private String type;

    public ShoppingDetails(ShoppingItem[] items, String type) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
