package com.sipios.refactoring.service;

import com.sipios.refactoring.models.Body;
import com.sipios.refactoring.models.Item;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;

@Service
public class ShoppingService {

    public String getPrice(Body body, Calendar cal) {
        double price = 0;
        double discount;

        discount = getDiscount(body);

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
            if (body.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < body.getItems().length; i++) {
                Item it = body.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    price += 30 * it.getNb() * discount;
                } else if (it.getType().equals("DRESS")) {
                    price += 50 * it.getNb() * discount;
                } else if (it.getType().equals("JACKET")) {
                    price += 100 * it.getNb() * discount;
                }
            }
        } else {
            if (body.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < body.getItems().length; i++) {
                Item it = body.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    price += 30 * it.getNb() * discount;
                } else if (it.getType().equals("DRESS")) {
                    price += 50 * it.getNb() * 0.8 * discount;
                } else if (it.getType().equals("JACKET")) {
                    price += 100 * it.getNb() * 0.9 * discount;
                }
            }
        }

        try {
            if (body.getType().equals("STANDARD_CUSTOMER")) {
                if (price > 200) {
                    throw new Exception("Price (" + price + ") is too high for standard customer");
                }
            } else if (body.getType().equals("PREMIUM_CUSTOMER")) {
                if (price > 800) {
                    throw new Exception("Price (" + price + ") is too high for premium customer");
                }
            } else if (body.getType().equals("PLATINUM_CUSTOMER")) {
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

    private double getDiscount(Body body) {
        double discount;
        if (body.getType().equals("STANDARD_CUSTOMER")) {
            discount = 1;
        } else if (body.getType().equals("PREMIUM_CUSTOMER")) {
            discount = 0.9;
        } else if (body.getType().equals("PLATINUM_CUSTOMER")) {
            discount = 0.5;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return discount;
    }


}
