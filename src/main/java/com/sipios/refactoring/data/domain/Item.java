package com.sipios.refactoring.data.domain;

/**
 * A {@code Item} is...
 * TODO: write javadoc...
 */
public class Item {

    private final ItemType itemType;
    private final int quantity;

    public Item(ItemType itemType, int quantity) {
        this.itemType = itemType;
        this.quantity = quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getQuantity() {
        return quantity;
    }

}
