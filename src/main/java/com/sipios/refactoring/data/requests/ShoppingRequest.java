package com.sipios.refactoring.data.requests;

public class ShoppingRequest {

    private ItemRequest[] items;
    private String type;

    public ShoppingRequest(ItemRequest[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public ShoppingRequest() {}

    public ItemRequest[] getItems() {
        return items;
    }

    public void setItems(ItemRequest[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
