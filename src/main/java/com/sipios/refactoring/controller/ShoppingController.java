package com.sipios.refactoring.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sipios.refactoring.models.dtos.ShoppingCartRequest;
import com.sipios.refactoring.services.PriceService;
import com.sipios.refactoring.services.PriceServiceLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/shopping")
@Import(PriceServiceLocal.class)
public class ShoppingController {

    PriceService priceService;
    public ShoppingController(@Autowired PriceService priceService) {
        this.priceService = priceService;
    }

    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

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


    @PostMapping
    public String getPrice(@RequestBody ShoppingCartRequest cartRequest) {
        if (cartRequest.getItems() == null || cartRequest.getItems().length == 0) {
            return "0";
        }
        double totalPrice = priceService.getTotalPrice(cartRequest);

        if (totalPrice > customerLimitPrice.getOrDefault(cartRequest.getType(),customerLimitPrice.get("STANDARD_CUSTOMER"))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price (" + totalPrice + ") is too high for " + logCustomerType.getOrDefault(cartRequest.getType(),logCustomerType.get("STANDARD_CUSTOMER")));
        }

        return String.valueOf(totalPrice);
    }
}

