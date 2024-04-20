package com.sipios.refactoring.controller;

import com.sipios.refactoring.dtos.Purchase;
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

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    private final ShoppingService shoppingService  ;
    private ShoppingController(ShoppingService shoppingService){
        this.shoppingService = shoppingService;
    }

    @PostMapping
    public String getPrice(@RequestBody @Valid Purchase b, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {

            var fieldErrors = bindingResult.getFieldErrors().stream()
                .map(e-> String.format("%s: %s",e.getField(), e.getDefaultMessage()))
                .reduce((a,s) -> String.format("%s%n%s", a,s)).orElseThrow();

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldErrors);
        }
        double p  =  shoppingService.getPriceByDate(LocalDate.now() , b.getCustomerType() , b.getPurchasedItems()) ;

        try {
            if (b.getCustomerType().equals("STANDARD_CUSTOMER")) {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            } else if (b.getCustomerType().equals("PREMIUM_CUSTOMER")) {
                if (p > 800) {
                    throw new Exception("Price (" + p + ") is too high for premium customer");
                }
            } else if (b.getCustomerType().equals("PLATINUM_CUSTOMER")) {
                if (p > 2000) {
                    throw new Exception("Price (" + p + ") is too high for platinum customer");
                }
            } else {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(p);
    }
}

