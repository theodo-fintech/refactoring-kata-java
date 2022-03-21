package com.sipios.refactoring.models;

import java.util.Arrays;
import java.util.StringJoiner;

public class CalculatePriceRequest {

    private Item[] items;
    private CalculatePriceRequestType type;

    public CalculatePriceRequest(Item[] is, CalculatePriceRequestType t) {
        this.items = is;
        this.type = t;
    }

    public CalculatePriceRequest() {
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public CalculatePriceRequestType getType() {
        return type;
    }

    public void setType(CalculatePriceRequestType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CalculatePriceRequest.class.getSimpleName() + "[", "]")
            .add("items=" + Arrays.toString(items))
            .add("type=" + type)
            .toString();
    }
}
