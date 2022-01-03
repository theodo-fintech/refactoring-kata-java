package com.sipios.refactoring.controller;

import com.sipios.refactoring.model.ShoppingCart;
import com.sipios.refactoring.service.CustomerService;
import com.sipios.refactoring.service.ShoppingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {
    @PostMapping
    public String getPrice(@RequestBody ShoppingCart b) {
        double customerDiscount = CustomerService.computeCustomerDiscount(b.getCustomerType());

        double totalPrice = ShoppingService.computeTotalPrice(b, customerDiscount);
        try {
            switch (b.getCustomerType()) {
                case STANDARD_CUSTOMER:
                    if (totalPrice > 200) {
                        throw new Exception("Price (" + totalPrice + ") is too high for standard customer");
                    }
                    break;
                case PREMIUM_CUSTOMER:
                    if (totalPrice > 800) {
                        throw new Exception("Price (" + totalPrice + ") is too high for premium customer");
                    }
                    break;
                case PLATINUM_CUSTOMER:
                    if (totalPrice > 2000) {
                        throw new Exception("Price (" + totalPrice + ") is too high for platinum customer");
                    }
                    break;
                default:
                    if (totalPrice > 200) {
                        throw new Exception("Price (" + totalPrice + ") is too high for standard customer");
                    }
                    break;
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(totalPrice);
    }

}


