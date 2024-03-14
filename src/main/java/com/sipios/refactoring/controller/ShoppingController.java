package com.sipios.refactoring.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
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

    @PostMapping
    public String getPrice(@RequestBody Body body) {
        double discount;

        // Compute discount for customer
        if (body.getType().equals("STANDARD_CUSTOMER")) {
            discount = 1;
        } else if (body.getType().equals("PREMIUM_CUSTOMER")) {
            discount = 0.9;
        } else if (body.getType().equals("PLATINUM_CUSTOMER")) {
            discount = 0.5;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (body.getItems() == null) {
            return "0";
        }

        double price = 0;

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (isNotWinterOrSummerDiscountsPeriods()) {
            for (int i = 0; i < body.getItems().length; i++) {
                Item it = body.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    price += 30 * it.getNb() * discount;
                } else if (it.getType().equals("DRESS")) {
                    price += 50 * it.getNb() * discount;
                } else if (it.getType().equals("JACKET")) {
                    price += 100 * it.getNb() * discount;
                }
            }
        } else {

            for (int i = 0; i < body.getItems().length; i++) {
                Item it = body.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    price += 30 * it.getNb() * discount;
                } else if (it.getType().equals("DRESS")) {
                    price += 50 * it.getNb() * 0.8 * discount;
                } else if (it.getType().equals("JACKET")) {
                    price += 100 * it.getNb() * 0.9 * discount;
                }
            }
        }

        try {
            if (body.getType().equals("STANDARD_CUSTOMER") && price > 200) {
                throw new Exception("Price (" + price + ") is too high for standard customer");
            } else if (body.getType().equals("PREMIUM_CUSTOMER") && price > 800) {
                throw new Exception("Price (" + price + ") is too high for premium customer");
            } else if (body.getType().equals("PLATINUM_CUSTOMER") && price > 2000) {
                throw new Exception("Price (" + price + ") is too high for platinum customer");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(price);
    }

    protected boolean isNotWinterOrSummerDiscountsPeriods() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        return !(
            cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                cal.get(Calendar.MONTH) == 5
        ) &&
            !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                    cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                    cal.get(Calendar.MONTH) == 0
            );
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
