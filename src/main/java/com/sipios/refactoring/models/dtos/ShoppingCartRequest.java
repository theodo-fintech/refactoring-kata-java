package com.sipios.refactoring.models.dtos;

public class ShoppingCartRequest {

    private CartItem[] cartItems;
    private String type;

    public ShoppingCartRequest(CartItem[] is, String t) {
        this.cartItems = is;
        this.type = t;
    }

    public ShoppingCartRequest() {
    }

    public CartItem[] getItems() {
        return cartItems;
    }

    public void setItems(CartItem[] cartItems) {
        this.cartItems = cartItems;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
