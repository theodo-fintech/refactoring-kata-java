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

    // TODO: not used
    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    // TODO: no route value, should return an int
    // Method is way too long, need to split it
    @PostMapping
    public String getPrice(@RequestBody Body b) {
        if (b.getItems() == null) {
            return "0";
        }
        double price = 0;
        double discount = 1;


        // TODO: type should be an enum
        // switch case
        // Compute discount for customer
        discount = b.getCustomer().getDiscount();

        // TODO: should be a function
        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        // TODO: bad way too present if + repetition
        // bad way to compare dates
        // dangerous design for unit testing, behaviour can change depending on the date
        // date should be an argument which can be set
        if (!isDateDiscount()) {
            // TODO: foreach
            for (Item it : b.getItems()) {
                price += it.getArticle().getPrice() * it.getNb() * discount;
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        } else {
            // TODO: code duplication
            for (Item it : b.getItems()) {
                price += it.getArticle().getPrice() * it.getNb() * it.getArticle().getSeasonal_discount() * discount;
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        }

        try {
            if (price > b.getCustomer().getThreshold()) {
                throw new Exception("Price (" + price + ") is too high for this customer");
            }
        } catch (Exception e) {
            // TODO: could be a custom exception
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(price);
    }

    public boolean isDateDiscount() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        return (cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5 && (cal.get(Calendar.MONTH) == Calendar.JANUARY || cal.get(Calendar.MONTH) == Calendar.JUNE));
    }
}
