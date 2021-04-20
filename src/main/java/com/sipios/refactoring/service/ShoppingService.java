package com.sipios.refactoring.service;

import com.sipios.refactoring.dto.BodyDto;
import com.sipios.refactoring.dto.ItemDto;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

public class ShoppingService {

    public String getPrice(BodyDto b) {
        double p = 0;
        double d;

        if (b.getItems() == null) {
            return "0";
        }

        d = this.calculateDiscountForCustomerType(b.getType());
        p = this.calculatePrice(b.getItems(), d);

        try {
            if (b.getType().equals("STANDARD_CUSTOMER")) {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            } else if (b.getType().equals("PREMIUM_CUSTOMER")) {
                if (p > 800) {
                    throw new Exception("Price (" + p + ") is too high for premium customer");
                }
            } else if (b.getType().equals("PLATINUM_CUSTOMER")) {
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

    /**
     * Calculate the discount to apply based on given customer type
     * @param type Customer type
     * @return Discount calculated
     */
    private double calculateDiscountForCustomerType(String type){
        double discount = 1;

        // Compute discount for customer
        if (type.equals("STANDARD_CUSTOMER")) {
            discount = 1;
        } else if (type.equals("PREMIUM_CUSTOMER")) {
            discount = 0.9;
        } else if (type.equals("PLATINUM_CUSTOMER")) {
            discount = 0.5;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return discount;
    }

    /**
     * Calculate total price based on list of items and discount
     * @param listItem List of items to use to calculte price
     * @param discount Discount that can be applied to every items
     * @return Price calculated
     */
    private double calculatePrice(ItemDto[] listItem, double discount){
        double price = 0;

        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

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
            for (int i = 0; i < listItem.length; i++) {
                ItemDto it = listItem[i];

                if (it.getType().equals("TSHIRT")) {
                    price += 30 * it.getNb() * discount;
                } else if (it.getType().equals("DRESS")) {
                    price += 50 * it.getNb() * discount;
                } else if (it.getType().equals("JACKET")) {
                    price += 100 * it.getNb() * discount;
                }
            }
        } else {
            for (int i = 0; i < listItem.length; i++) {
                ItemDto it = listItem[i];

                if (it.getType().equals("TSHIRT")) {
                    price += 30 * it.getNb() * discount;
                } else if (it.getType().equals("DRESS")) {
                    price += 50 * it.getNb() * 0.8 * discount;
                } else if (it.getType().equals("JACKET")) {
                    price += 100 * it.getNb() * 0.9 * discount;
                }
            }
        }

        return price;
    }
}
