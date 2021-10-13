package com.sipios.refactoring.controller;

import com.sipios.refactoring.ItemType;

public


// TODO: bad name
class Item {

    // TODO: enum here
    private ItemType type;
    private int nb;

    public Item() {}

    public Item(ItemType type, int quantity) {
        this.type = type;
        this.nb = quantity;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}

