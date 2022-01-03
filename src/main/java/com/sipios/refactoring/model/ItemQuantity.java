package com.sipios.refactoring.model;

public class ItemQuantity {

    private final String type;
    private final int nb;

    public ItemQuantity(String type, int quantity) {
        this.type = type;
        this.nb = quantity;
    }

    public String getType() {
        return type;
    }

    public int getNb() {
        return nb;
    }

}
