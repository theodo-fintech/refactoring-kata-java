package com.sipios.refactoring.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.sipios.refactoring.dto.ShoppingDetails;
import com.sipios.refactoring.dto.ShoppingItem;
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
    public String getPrice(@RequestBody ShoppingDetails s) {
        double p = 0;
        double d;

        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        // Compute discount for customer
        if (s.getType().equals("STANDARD_CUSTOMER")) {
            d = 1;
        } else if (s.getType().equals("PREMIUM_CUSTOMER")) {
            d = 0.9;
        } else if (s.getType().equals("PLATINUM_CUSTOMER")) {
            d = 0.5;
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
            if (s.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < s.getItems().length; i++) {
                ShoppingItem it = s.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    p += 30 * it.getNb() * d;
                } else if (it.getType().equals("DRESS")) {
                    p += 50 * it.getNb() * d;
                } else if (it.getType().equals("JACKET")) {
                    p += 100 * it.getNb() * d;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        } else {
            if (s.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < s.getItems().length; i++) {
                ShoppingItem it = s.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    p += 30 * it.getNb() * d;
                } else if (it.getType().equals("DRESS")) {
                    p += 50 * it.getNb() * 0.8 * d;
                } else if (it.getType().equals("JACKET")) {
                    p += 100 * it.getNb() * 0.9 * d;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        }

        try {
            if (s.getType().equals("STANDARD_CUSTOMER")) {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            } else if (s.getType().equals("PREMIUM_CUSTOMER")) {
                if (p > 800) {
                    throw new Exception("Price (" + p + ") is too high for premium customer");
                }
            } else if (s.getType().equals("PLATINUM_CUSTOMER")) {
                if (p > 2000) {
                    throw new Exception("Price (" + p + ") is too high for platinum customer");
                }
            } else {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(p);
    }
}
