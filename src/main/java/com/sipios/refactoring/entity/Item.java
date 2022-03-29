package com.sipios.refactoring.entity;

public class Item {

    private ProductType type;
    private int nb;

    public Item() {
    }

    public Item(ProductType type, int amount) {
        this.type = type;
        this.nb = amount;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}
