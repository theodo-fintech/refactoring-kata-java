package com.sipios.refactoring.service;

import com.sipios.refactoring.dto.BodyDto;
import com.sipios.refactoring.dto.ItemDto;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.text.MessageFormat;
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
     * Calculate total price for a list of items
     * @param b Data to use to calculate the price
     * @return Price calculated
     */
    public String getPrice(BodyDto b) throws Exception {
        // if there is no items, then stop process and return 0 as the price
        if (b.getItems() == null) {
            return "0";
        }

        double discount = this.calculateDiscountForCustomerType(b.getType());
        double price = this.calculatePrice(b.getItems(), discount);

        // Handle max price for every customer type
        if (PREMIUM_CUSTOMER.equals(b.getType()) && price > 800) {
            throw new Exception(MessageFormat.format("Price ({0}) is too high for premium customer", String.valueOf(price)));
        } else if (PLATINUM_CUSTOMER.equals(b.getType()) && price > 2000) {
            throw new Exception(MessageFormat.format("Price ({0}) is too high for platinum customer", String.valueOf(price)));
        } else if(STANDARD_CUSTOMER.equals(b.getType()) && price > 200){
            throw new Exception(MessageFormat.format("Price ({0}) is too high for standard customer", String.valueOf(price)));
        }
        
        return String.valueOf(price);
    }

    /**
     * Calculate the discount to apply based on given customer type
     * @param type Customer type
     * @return Discount calculated
     */
    private double calculateDiscountForCustomerType(String type) throws Exception {
        double discount = 1;

        if (STANDARD_CUSTOMER.equals(type)) {
            discount = 1;
        } else if (PREMIUM_CUSTOMER.equals(type)) {
            discount = 0.9;
        } else if (PLATINUM_CUSTOMER.equals(type)) {
            discount = 0.5;
        } else {
            throw new Exception("Cannot identify the customer type");
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

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
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
