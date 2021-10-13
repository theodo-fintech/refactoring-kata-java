package com.sipios.refactoring.controller;

public


// TODO: bad name
class Item {

    // TODO: enum here
    private String type;
    private int nb;

    public Item() {}

    public Item(String type, int quantity) {
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

