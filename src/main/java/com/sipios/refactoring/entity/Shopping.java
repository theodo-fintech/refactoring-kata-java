package com.sipios.refactoring.entity;

public class Shopping {

    private Item[] items;
    private ConsumerType type;

    public Shopping(Item[] items, ConsumerType consumerType) {
        this.items = items;
        this.type = consumerType;
    }

    public Shopping() {
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public ConsumerType getType() {
        return type;
    }

    public void setType(ConsumerType type) {
        this.type = type;
    }
}
