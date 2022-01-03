package com.sipios.refactoring.service;

import com.sipios.refactoring.model.ShoppingCart;
import com.sipios.refactoring.model.ItemQuantity;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ShoppingService {

    public static double computeTotalPrice(ShoppingCart b, double customerDiscount) {
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
                return 0;
            }

            for (int i = 0; i < b.getItems().length; i++) {
                ItemQuantity it = b.getItems()[i];

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
                return 0;
            }

            for (int i = 0; i < b.getItems().length; i++) {
                ItemQuantity it = b.getItems()[i];

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
        return totalPrice;
    }
}
