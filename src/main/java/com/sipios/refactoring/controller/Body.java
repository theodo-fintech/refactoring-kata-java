package com.sipios.refactoring.controller;

import com.sipios.refactoring.CustomerType;

// TODO: bad name
public class Body {

    private Item[] items;
    // TODO: enum here
    private CustomerType type;

    public Body(Item[] is, CustomerType t) {
        this.items = is;
        this.type = t;
    }

    public Body() {}

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }
}
