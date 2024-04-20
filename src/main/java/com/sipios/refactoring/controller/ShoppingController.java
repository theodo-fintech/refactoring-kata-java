package com.sipios.refactoring.controller;

import com.sipios.refactoring.dtos.Purchase;
import com.sipios.refactoring.exceptions.FunctionalException;
import com.sipios.refactoring.services.ShoppingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    private final ShoppingService shoppingService;

    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping
    public String getPrice(@RequestBody @Valid Purchase b, BindingResult bindingResult) {
        logger.info("get price of purchase: {}", b);
        if (bindingResult.hasFieldErrors()) {

            var fieldErrors = bindingResult.getFieldErrors().stream()
                .map(e -> String.format("%s: %s", e.getField(), e.getDefaultMessage()))
                .reduce((a, s) -> String.format("%s%n%s", a, s)).orElseThrow();

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldErrors);
        }
        double p;
        try {
            p = shoppingService.getPriceByDate(LocalDate.now(ZoneId.of("Europe/Paris")), b.getCustomerType(), b.getPurchasedItems());

        } catch (FunctionalException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(p);
    }
}

