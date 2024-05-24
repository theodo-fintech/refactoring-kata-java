package com.sipios.refactoring.data.domain;

/**
 * A {@code Item} is...
 * TODO: write javadoc...
 */
public class Item {

    private final ItemType type;
    private final int quantity;

    public Item(ItemType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public ItemType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

}
