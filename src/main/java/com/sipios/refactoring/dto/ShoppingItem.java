package com.sipios.refactoring.dto;

import com.sipios.refactoring.utils.enums.ShoppingItemType;

public class ShoppingItem {
    private ShoppingItemType type;
    private int nb;

    public ShoppingItem() {}

    public ShoppingItem(ShoppingItemType type, int quantity) {
        this.type = type;
        this.nb = quantity;
    }

    public ShoppingItemType getType() {
        return type;
    }

    public void setType(ShoppingItemType type) {
        this.type = type;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}
