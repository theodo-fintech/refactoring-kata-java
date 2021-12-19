package com.sipios.refactoring.dto;

public class BodyDto {

    private ItemDto[] items;
    private String type;

    public BodyDto(ItemDto[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public BodyDto() {}

    public ItemDto[] getItems() {
        return items;
    }

    public void setItems(ItemDto[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}