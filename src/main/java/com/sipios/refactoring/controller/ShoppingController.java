package com.sipios.refactoring.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sipios.refactoring.models.dtos.Body;
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

    private static final Map<String,Double> customerPlanDiscounts = Stream.of(new Object[][] {
        { "STANDARD_CUSTOMER", 1d },
        { "PREMIUM_CUSTOMER", 0.9d },
        { "PLATINUM_CUSTOMER", 0.5d }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

    private static final Map<String,Double> lowSeasonDiscounts = Stream.of(new Object[][] {
        { "TSHIRT", 1d },
        { "DRESS", 0.9d },
        { "JACKET", 0.5d }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));


    private static final Map<String,Double> itemPriceList = Stream.of(new Object[][] {
        { "TSHIRT", 30d },
        { "DRESS", 50d },
        { "JACKET", 100d }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

    private static final Map<String,String> logCustomerType = Stream.of(new Object[][] {
        { "STANDARD_CUSTOMER", "standard" },
        { "PREMIUM_CUSTOMER", "premium" },
        { "PLATINUM_CUSTOMER", "platinium" }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));

    private static final Map<String,Double> customerLimitPrice = Stream.of(new Object[][] {
        { "STANDARD_CUSTOMER", 200d },
        { "PREMIUM_CUSTOMER", 800d },
        { "PLATINUM_CUSTOMER", 2000d }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));


    private double computeDiscount(String type){
        if ( !customerPlanDiscounts.containsKey(type)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            return customerPlanDiscounts.get(type).doubleValue();
        }
    }

    private boolean isSeasonalDiscountPeriod(Calendar cal){
        return !(
        cal.get(Calendar.DAY_OF_MONTH) < 15 &&
        cal.get(Calendar.DAY_OF_MONTH) > 5 &&
        cal.get(Calendar.MONTH) == Calendar.JUNE
            ) &&
                !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
        cal.get(Calendar.DAY_OF_MONTH) > 5 &&
        cal.get(Calendar.MONTH) == Calendar.JANUARY
            ) ;
    }
    @PostMapping
    public String getPrice(@RequestBody Body b) {
        if (b.getItems() == null || b.getItems().length == 0) {
            return "0";
        }
        double totalPrice = 0;
        double discount;

        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        discount = computeDiscount(b.getType());

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (isSeasonalDiscountPeriod(cal)) {


            totalPrice = Arrays.stream(b.getItems())
                .mapToDouble(item -> itemPriceList.getOrDefault(item.getType(), 0d) * item.getNb() * discount)
                .sum();

        } else {
            totalPrice = Arrays.stream(b.getItems())
                .mapToDouble(item -> itemPriceList.getOrDefault(item.getType(), 0d) * item.getNb() * discount * lowSeasonDiscounts.getOrDefault(item.getType(),1d))
                .sum();
        }

        if (totalPrice > customerLimitPrice.getOrDefault(b.getType(),customerLimitPrice.get("STANDARD_CUSTOMER"))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price (" + totalPrice + ") is too high for " + logCustomerType.getOrDefault(b.getType(),logCustomerType.get("STANDARD_CUSTOMER")));
        }

        return String.valueOf(totalPrice);
    }
}

