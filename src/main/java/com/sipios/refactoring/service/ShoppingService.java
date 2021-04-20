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

/**
 * ShoppingService class
 */
public class ShoppingService {

    final String STANDARD_CUSTOMER = "STANDARD_CUSTOMER";
    final String PREMIUM_CUSTOMER = "PREMIUM_CUSTOMER";
    final String PLATINUM_CUSTOMER = "PLATINUM_CUSTOMER";

    final String TSHIRT = "TSHIRT";
    final String DRESS = "DRESS";
    final String JACKET = "JACKET";

    /**
     * 
     * @param b Data to use to calculate the price
     * @return Price calculated
     */
    public String getPrice(BodyDto b) {
        // if there is no items, then stop process and return 0 as the price
        if (b.getItems() == null) {
            return "0";
        }

        double discount = this.calculateDiscountForCustomerType(b.getType());
        double price = this.calculatePrice(b.getItems(), discount);

        try {
            if (STANDARD_CUSTOMER.equals(b.getType())) {
                if (price > 200) {
                    throw new Exception("Price (" + price + ") is too high for standard customer");
                }
            } else if (PREMIUM_CUSTOMER.equals(b.getType())) {
                if (price > 800) {
                    throw new Exception("Price (" + price + ") is too high for premium customer");
                }
            } else if (PLATINUM_CUSTOMER.equals(b.getType())) {
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

    /**
     * Calculate the discount to apply based on given customer type
     * @param type Customer type
     * @return Discount calculated
     */
    private double calculateDiscountForCustomerType(String type){
        double discount = 1;

        if (STANDARD_CUSTOMER.equals(type)) {
            discount = 1;
        } else if (PREMIUM_CUSTOMER.equals(type)) {
            discount = 0.9;
        } else if (PLATINUM_CUSTOMER.equals(type)) {
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
                (cal.get(Calendar.MONTH) == 0 || cal.get(Calendar.MONTH) == 5)
            )
        ) {
            for (int i = 0; i < listItem.length; i++) {
                if (TSHIRT.equals(listItem[i].getType())) {
                    price += 30 * listItem[i].getNb() * discount;
                } else if (DRESS.equals(listItem[i].getType())) {
                    price += 50 * listItem[i].getNb() * discount;
                } else if (JACKET.equals(listItem[i].getType())) {
                    price += 100 * listItem[i].getNb() * discount;
                }
            }
        } else {
            for (int i = 0; i < listItem.length; i++) {
                if (TSHIRT.equals(listItem[i].getType())) {
                    price += 30 * listItem[i].getNb() * discount;
                } else if (DRESS.equals(listItem[i].getType())) {
                    price += 50 * listItem[i].getNb() * 0.8 * discount;
                } else if (JACKET.equals(listItem[i].getType())) {
                    price += 100 * listItem[i].getNb() * 0.9 * discount;
                }
            }
        }

        return price;
    }
}
