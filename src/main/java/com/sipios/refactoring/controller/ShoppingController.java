package com.sipios.refactoring.controller;

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
    @PostMapping
    public String getPrice(@RequestBody Body b) {
        double customerDiscount = computeCustomerDiscount(b.getCustomerType());

        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        double totalPrice = 0;
        if (
            !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                    cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                    cal.get(Calendar.MONTH) == Calendar.JUNE
            ) &&
                !(
                    cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                        cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                        cal.get(Calendar.MONTH) == Calendar.JANUARY
                )
        ) {
            if (b.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                switch (it.getType()) {
                    case "TSHIRT":
                        totalPrice += 30 * it.getNb() * customerDiscount;
                        break;
                    case "DRESS":
                        totalPrice += 50 * it.getNb() * customerDiscount;
                        break;
                    case "JACKET":
                        totalPrice += 100 * it.getNb() * customerDiscount;
                        break;
                }
            }
        } else {
            if (b.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                switch (it.getType()) {
                    case "TSHIRT":
                        totalPrice += 30 * it.getNb() * customerDiscount;
                        break;
                    case "DRESS":
                        totalPrice += 50 * it.getNb() * 0.8 * customerDiscount;
                        break;
                    case "JACKET":
                        totalPrice += 100 * it.getNb() * 0.9 * customerDiscount;
                        break;
                }
            }
        }

        try {
            switch (b.getCustomerType()) {
                case "STANDARD_CUSTOMER":
                    if (totalPrice > 200) {
                        throw new Exception("Price (" + totalPrice + ") is too high for standard customer");
                    }
                    break;
                case "PREMIUM_CUSTOMER":
                    if (totalPrice > 800) {
                        throw new Exception("Price (" + totalPrice + ") is too high for premium customer");
                    }
                    break;
                case "PLATINUM_CUSTOMER":
                    if (totalPrice > 2000) {
                        throw new Exception("Price (" + totalPrice + ") is too high for platinum customer");
                    }
                    break;
                default:
                    if (totalPrice > 200) {
                        throw new Exception("Price (" + totalPrice + ") is too high for standard customer");
                    }
                    break;
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(totalPrice);
    }

    double computeCustomerDiscount(String customerType) {
        final double customerDiscount;
        switch (customerType) {
            case "STANDARD_CUSTOMER":
                customerDiscount = 1;
                break;
            case "PREMIUM_CUSTOMER":
                customerDiscount = 0.9;
                break;
            case "PLATINUM_CUSTOMER":
                customerDiscount = 0.5;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return customerDiscount;
    }
}

class Body {

    private final Item[] items;
    private final String customerType;

    public Body(Item[] is, String t) {
        this.items = is;
        this.customerType = t;
    }

    public Item[] getItems() {
        return items;
    }

    public String getCustomerType() {
        return customerType;
    }
}

class Item {

    private final String type;
    private final int nb;

    public Item(String type, int quantity) {
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
