package fr.sipios.refactoring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    @GetMapping
    public String getPrice() {
        return "12";
    }
}
