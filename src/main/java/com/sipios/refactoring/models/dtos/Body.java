package com.sipios.refactoring.models.dtos;

public class Body {

    private Item[] items;
    private String type;

    public Body(Item[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public Body() {
    }

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
