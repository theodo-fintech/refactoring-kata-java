package com.sipios.refactoring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @PostMapping
    public String getPrice(@RequestBody ShoppingCart shoppingCart) {
        double price = 0;
        double customerDiscount;

        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        // Compute discount for customer
        if (shoppingCart.getType().equals("STANDARD_CUSTOMER")) {
            customerDiscount = 1;
        } else if (shoppingCart.getType().equals("PREMIUM_CUSTOMER")) {
            customerDiscount = 0.9;
        } else if (shoppingCart.getType().equals("PLATINUM_CUSTOMER")) {
            customerDiscount = 0.5;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (
            !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                cal.get(Calendar.MONTH) == 5
            ) &&
            !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                cal.get(Calendar.MONTH) == 0
            )
        ) {
            if (shoppingCart.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < shoppingCart.getItems().length; i++) {
                Item item = shoppingCart.getItems()[i];

                if (item.getType().equals("TSHIRT")) {
                    price += 30 * item.getQuantity() * customerDiscount;
                } else if (item.getType().equals("DRESS")) {
                    price += 50 * item.getQuantity() * customerDiscount;
                } else if (item.getType().equals("JACKET")) {
                    price += 100 * item.getQuantity() * customerDiscount;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        } else {
            if (shoppingCart.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < shoppingCart.getItems().length; i++) {
                Item item = shoppingCart.getItems()[i];

                if (item.getType().equals("TSHIRT")) {
                    price += 30 * item.getQuantity() * customerDiscount;
                } else if (item.getType().equals("DRESS")) {
                    price += 50 * item.getQuantity() * 0.8 * customerDiscount;
                } else if (item.getType().equals("JACKET")) {
                    price += 100 * item.getQuantity() * 0.9 * customerDiscount;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        }

        try {
            if (shoppingCart.getType().equals("STANDARD_CUSTOMER")) {
                if (price > 200) {
                    throw new Exception("Price (" + price + ") is too high for standard customer");
                }
            } else if (shoppingCart.getType().equals("PREMIUM_CUSTOMER")) {
                if (price > 800) {
                    throw new Exception("Price (" + price + ") is too high for premium customer");
                }
            } else if (shoppingCart.getType().equals("PLATINUM_CUSTOMER")) {
                if (price > 2000) {
                    throw new Exception("Price (" + price + ") is too high for platinum customer");
                }
            } else {
                if (price > 200) {
                    throw new Exception("Price (" + price + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(price);
    }
}

class ShoppingCart {

    private Item[] items;
    private String type;

    public ShoppingCart(Item[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public ShoppingCart() {}

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
    private int quantity;

    public Item() {}

    public Item(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
