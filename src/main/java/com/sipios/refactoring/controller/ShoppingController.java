package com.sipios.refactoring.controller;

import com.sipios.refactoring.model.Body;
import com.sipios.refactoring.service.ShoppingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    public ShoppingService shoppingService;


    @PostMapping
    public String getPrice(@RequestBody Body b) {
        return shoppingService.getPrice(b);
    }
}

