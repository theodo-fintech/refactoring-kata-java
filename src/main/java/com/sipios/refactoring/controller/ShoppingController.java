package com.sipios.refactoring.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.List;
import java.util.ArrayList;

import com.sipios.refactoring.dto.BodyDto;
import com.sipios.refactoring.dto.ItemDto;
import com.sipios.refactoring.service.ShoppingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @PostMapping
    public String getPrice(@RequestBody Body b) {
        final ShoppingService shoppingService = new ShoppingService();
        final BodyDto bodyDto = convertBodyToBodyDto(b);

        return shoppingService.getPrice(bodyDto);
    }

    /**
     * Convert Body object to BodyDto object
     * @param b Body object to convert
     * @return BodyDto created from Body
     */
    private BodyDto convertBodyToBodyDto(Body b){
        // First we convert All Item object to ItemDto object and then we can create our BodyDto object
        final List<ItemDto> listItemDto = new ArrayList<>();
        for (Item i : b.getItems()) {
            listItemDto.add(new ItemDto(i.getType(), i.getNb()));
        }

        ItemDto[] arrayItemDto = new ItemDto[b.getItems().length];
        arrayItemDto = listItemDto.toArray(arrayItemDto);
        return new BodyDto(arrayItemDto, b.getType());
    }
}

class Body {

    private Item[] items;
    private String type;

    public Body(Item[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public Body() {}

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

class Item {

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
