package com.sipios.refactoring.service;

import com.sipios.refactoring.dto.ShoppingDetails;
import com.sipios.refactoring.dto.ShoppingItem;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
public class ShoppingService {

    public double getPrice (ShoppingDetails s) throws Exception {
        double priceShopping = 0;
        double customerDiscount;

        if (s.getItems() == null) {
            return priceShopping;
        }

        // Compute discount for customer
        customerDiscount = getCustomerDiscount(s.getType());

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        ShoppingItem it;
        for (int i = 0; i < s.getItems().length; i++) {
            it = s.getItems()[i];
            try {
                priceShopping += getItemBasePrice(it)
                    * it.getNb()
                    * getItemDiscount(it, cal)
                    * customerDiscount;
            } catch (Exception e) {
                // TODO: handle invalid item
            }
        }
        return priceShopping;
    }

    private double getCustomerDiscount(String type) throws Exception {
        switch (type) {
            case "STANDARD_CUSTOMER":
                return 1;
            case "PREMIUM_CUSTOMER":
                return 0.9;
            case "PLATINUM_CUSTOMER":
                return 0.5;
            default:
                throw new Exception("Invalid Customer type");
        }
    }

    private boolean isSummerDiscountPeriod(Calendar cal) {
        return cal.get(Calendar.DAY_OF_MONTH) < 15 &&
            cal.get(Calendar.DAY_OF_MONTH) > 5 &&
            cal.get(Calendar.MONTH) == Calendar.JUNE;
    }

    private boolean isWinterDiscountPeriod(Calendar cal) {
        return cal.get(Calendar.DAY_OF_MONTH) < 15 &&
            cal.get(Calendar.DAY_OF_MONTH) > 5 &&
            cal.get(Calendar.MONTH) == Calendar.JANUARY;
    }

    private double getItemDiscount(ShoppingItem it, Calendar cal) throws Exception {
        // Check if its Summer / Winter discount period
        if (!isSummerDiscountPeriod(cal) && !isWinterDiscountPeriod(cal)) {
            return 1;
        }

        switch (it.getType()) {
            case "TSHIRT":
                return 1;
            case "DRESS":
                return 0.8;
            case "JACKET":
                return 0.9;
            // case "SWEATSHIRT":
            //
            default:
                throw new Exception("Invalid shopping item");
        }
    }

    private int getItemBasePrice(ShoppingItem it) throws Exception {
        switch (it.getType()) {
            case "TSHIRT":
                return 30;
            case "DRESS":
                return 50;
            case "JACKET":
                return 100;
            // case "SWEATSHIRT":
            //      return 80;
            default:
                throw new Exception("Invalid shopping item");
        }
    }
}
