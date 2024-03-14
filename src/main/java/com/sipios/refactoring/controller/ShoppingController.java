package com.sipios.refactoring.controller;

import com.sipios.refactoring.pricing.CustomerPlan;
import com.sipios.refactoring.pricing.Discounts;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Clock;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private final Clock clock;

    public ShoppingController(Clock clock) {
        this.clock = clock;
    }

    @PostMapping
    public String getPrice(@RequestBody Body body) {

        try {
            var customerPlan = CustomerPlan.valueOf(body.getType());

            if (body.getItems() == null) {
                return "0";
            }

            double price = 0;

            for (int i = 0; i < body.getItems().length; i++) {
                Item it = body.getItems()[i];

                switch (it.getType()) {
                    case "TSHIRT":
                        price += 30 * it.getNb() * applyItemTypeDiscount("TSHIRT") * customerPlan.getDiscount();
                        break;
                    case "DRESS":
                        price += 50 * it.getNb() * applyItemTypeDiscount("DRESS") * customerPlan.getDiscount();
                        break;
                    case "JACKET":
                        price += 100 * it.getNb() * applyItemTypeDiscount("JACKET") * customerPlan.getDiscount();
                        break;
                }
            }

            try {
                if (price > customerPlan.getTotalPriceThreshold()) {
                    throw new Exception("Price (" + price + ") is too high for " + customerPlan.getLabel() + " customer");
                }
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }

            return String.valueOf(price);

        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private double applyItemTypeDiscount(String itemType) {
       return Discounts.findItemTypeDiscount(itemType, clock)
           .map(Discounts::getDiscount)
           .orElse(1d);
    }
}

class Body {

    private Item[] items;
    private String type;

    public Body(Item[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public Body() {
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

class Item {

    private String type;
    private int nb;

    public Item() {
    }

    public Item(String type, int quantity) {
        this.type = type;
        this.nb = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}
