package com.sipios.refactoring.models.dtos;

public class CartItem {

    private String type;
    private int nb;

    public CartItem() {
    }

    public CartItem(String type, int quantity) {
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
