package com.sipios.refactoring.model;

public class Body {

    private Item[] items;
    private String clientType;

    public Body(Item[] is, String t) {
        this.items = is;
        this.clientType = t;
    }

    public Body() {
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
