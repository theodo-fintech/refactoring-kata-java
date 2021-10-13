package com.sipios.refactoring.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.sipios.refactoring.CustomerType;
import com.sipios.refactoring.ItemType;
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

    // TODO: not used
    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    // TODO: no route value, should return an int
    // Method is way too long, need to split it
    @PostMapping
    public String getPrice(@RequestBody Body b) {
        // TODO: bad names
        double p = 0;
        double d;


        // TODO: type should be an enum
        // switch case
        // Compute discount for customer
        if (b.getType().equals(CustomerType.STANDARD)) {
            d = 1;
        } else if (b.getType().equals(CustomerType.PREMIUM)) {
            d = 0.9;
        } else if (b.getType().equals(CustomerType.PLATINIUM)) {
            d = 0.5;
        } else {
            // TODO: Exception unclear
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // TODO: should be a function
        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        // TODO: bad way too present if + repetition
        // bad way to compare dates
        // dangerous design for unit testing, behaviour can change depending on the date
        // date should be an argument which can be set
        if (!isDateDiscount()) {
            // TODO: not clear (meaning?)
            if (b.getItems() == null) {
                return "0";
            }
            // TODO: foreach
            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                // TODO: item type should be an enum
                if (it.getType().equals(ItemType.TSHIRT)) {
                    p += 30 * it.getNb() * d;
                } else if (it.getType().equals(ItemType.DRESS)) {
                    p += 50 * it.getNb() * d;
                } else if (it.getType().equals(ItemType.JACKET)) {
                    p += 100 * it.getNb() * d;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        } else {
            // TODO: meaningless
            if (b.getItems() == null) {
                return "0";
            }
            // TODO: code duplication
            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                if (it.getType().equals(ItemType.TSHIRT)) {
                    p += 30 * it.getNb() * d;
                } else if (it.getType().equals(ItemType.DRESS)) {
                    p += 50 * it.getNb() * 0.8 * d;
                } else if (it.getType().equals(ItemType.JACKET)) {
                    p += 100 * it.getNb() * 0.9 * d;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        }

        try {
            if (b.getType().equals(CustomerType.STANDARD)) {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            } else if (b.getType().equals(CustomerType.PREMIUM)) {
                if (p > 800) {
                    throw new Exception("Price (" + p + ") is too high for premium customer");
                }
            } else if (b.getType().equals(CustomerType.PLATINIUM)) {
                if (p > 2000) {
                    throw new Exception("Price (" + p + ") is too high for platinum customer");
                }
            } else {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            // TODO: could be a custom exception
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(p);
    }

    public boolean isDateDiscount() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        return (cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5 && (cal.get(Calendar.MONTH) == Calendar.JANUARY || cal.get(Calendar.MONTH) == Calendar.JUNE));
    }
}
