package com.sipios.refactoring.controller;

import com.sipios.refactoring.models.CalculatePriceRequest;
import com.sipios.refactoring.services.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private PriceService priceService;

    private static final Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @PostMapping
    public String getPrice(@RequestBody @Validated CalculatePriceRequest calculatePriceRequest) {
        if (requestHasZeroItems(calculatePriceRequest)) {
            logger.info("Request has 0 items");
            return "0";
        }

        double discount = priceService.computeDiscountByType(calculatePriceRequest.getType());
        double price = priceService.calculatePrice(calculatePriceRequest, discount);
        priceService.priceAndRequestTypeCheck(calculatePriceRequest.getType(), price);

        logger.info("The price for this shopping request {} is : {}", calculatePriceRequest.toString(), price);
        return String.valueOf(price);
    }

    private boolean requestHasZeroItems(CalculatePriceRequest calculatePriceRequest) {
        return calculatePriceRequest.getItems() == null || calculatePriceRequest.getItems().length == 0;
    }
}

