package com.sipios.refactoring.controller;

import com.sipios.refactoring.data.domain.CustomerType;
import com.sipios.refactoring.data.requests.ItemRequest;
import com.sipios.refactoring.data.requests.ShoppingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @PostMapping
    public String getPrice(@RequestBody ShoppingRequest shoppingRequest) {
        double price = 0;

        CustomerType customerType;
        try {
            customerType = CustomerType.valueOf(shoppingRequest.getType().replaceAll("_CUSTOMER", ""));
        } catch (IllegalArgumentException exception) {
            String message = "Unexpected customer type: " + shoppingRequest.getType()
                + ". Supported values: " + Arrays.toString(CustomerType.values());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }

        double customerDiscount = customerType.getDiscount();



        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);


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
            if (shoppingRequest.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < shoppingRequest.getItems().length; i++) {
                ItemRequest item = shoppingRequest.getItems()[i];

                if (item.getType().equals("TSHIRT")) {
                    price += 30 * item.getQuantity() * customerDiscount;
                } else if (item.getType().equals("DRESS")) {
                    price += 50 * item.getQuantity() * customerDiscount;
                } else if (item.getType().equals("JACKET")) {
                    price += 100 * item.getQuantity() * customerDiscount;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        } else {
            if (shoppingRequest.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < shoppingRequest.getItems().length; i++) {
                ItemRequest item = shoppingRequest.getItems()[i];

                if (item.getType().equals("TSHIRT")) {
                    price += 30 * item.getQuantity() * customerDiscount;
                } else if (item.getType().equals("DRESS")) {
                    price += 50 * item.getQuantity() * 0.8 * customerDiscount;
                } else if (item.getType().equals("JACKET")) {
                    price += 100 * item.getQuantity() * 0.9 * customerDiscount;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        }



        if (!customerType.accepts(price)) {
            String message = "Price (" + price + ") is too high for "
                + customerType + " customer. "
                + "Limit is: " + customerType.getPriceLimit();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }

        return String.valueOf(price);
    }
}
