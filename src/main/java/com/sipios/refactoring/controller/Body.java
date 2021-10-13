package com.sipios.refactoring.controller;

import com.sipios.refactoring.customer.Customer;

// TODO: bad name
public class Body {

    private Item[] items;
    // TODO: enum here
    private Customer customer;

    public Body(Item[] is, Customer c) {
        this.items = is;
        this.customer = c;
    }

    public Body() {}

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer c) {
        this.customer = c;
    }
}
