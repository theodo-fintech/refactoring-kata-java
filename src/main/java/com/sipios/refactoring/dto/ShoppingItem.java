package com.sipios.refactoring.dto;

public class ShoppingItem {
    private String type;
    private int nb;

    public ShoppingItem() {}

    public ShoppingItem(String type, int quantity) {
        this.type = type;
        this.nb = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}
