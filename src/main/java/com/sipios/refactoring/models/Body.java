package com.sipios.refactoring.models;

public class Body {
    private Item[] items;
    private String type;

    public Body(Item[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public Body() {}

    public Item[] getItems() {
        return items;
    }

    public String getType() {
        return type;
    }
}
