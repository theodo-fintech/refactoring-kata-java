package com.sipios.refactoring.data.requests;

public class ShoppingCart {

    private Item[] items;
    private String type;

    public ShoppingCart(Item[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public ShoppingCart() {}

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
