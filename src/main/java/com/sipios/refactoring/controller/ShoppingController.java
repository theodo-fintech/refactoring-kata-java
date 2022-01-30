package com.sipios.refactoring.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// https://stackoverflow.com/questions/11887799/how-to-mock-new-date-in-java-using-mockito
interface DateTime {
    Date getDate();
}

@Component
class DateTimeImpl implements DateTime {
    @Override
    public Date getDate() {
       return new Date();
    }
}

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);
    
    @Autowired
    private DateTime dateTime;

    @PostMapping
    public String getPrice(@RequestBody Body b) {

        double p = 0;
        double d = b.getType().getDiscount();

        Date date = dateTime.getDate();
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
            if (b.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                p += it.getType().getPrice() * it.getNb() * d;
            }
        } else {
            if (b.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];
                p += it.getType().getDiscountedPrice() * it.getNb() * d;
            }
        }

        try {
            if (b.getType().equals(CustomerType.STANDARD_CUSTOMER)) {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            } else if (b.getType().equals(CustomerType.PREMIUM_CUSTOMER)) {
                if (p > 800) {
                    throw new Exception("Price (" + p + ") is too high for premium customer");
                }
            } else if (b.getType().equals(CustomerType.PLATINUM_CUSTOMER)) {
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

enum CustomerType{

    STANDARD_CUSTOMER(1,200, "standard customer"),
    PREMIUM_CUSTOMER(0.9,800, "premium customer"),
    PLATINUM_CUSTOMER(0.5, 2000, "platinium customer");

    private double discount;
    private double maxSpending;
    private String name;

    public double getDiscount()
    {
        return this.discount;
    }

    public double getMaxSpending()
    {
        return this.maxSpending;
    }

    public String getName()
    {
        return this.name;
    }

    private CustomerType(double discount, double maxSpending, String name)
    {
        this.discount = discount;
        this.maxSpending = maxSpending;
        this.name = name;
    }

}

class Body {

    private Item[] items;
    private CustomerType type;

    public Body(Item[] is, CustomerType t) {
        this.items = is;
        this.type = t;
    }

    public Body() {}

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }
}

enum ItemType{
    
    TSHIRT(30, 30),
    DRESS(50, 50*0.8),
    JACKET(100, 100*0.9);

    private double price;
    private double discountedPrice;

    public double getPrice()
    {
        return this.price;
    }

    public double getDiscountedPrice()
    {
        return this.discountedPrice;
    }

    private ItemType(double price, double discountedPrice)
    {
        this.price = price;
        this.discountedPrice = discountedPrice;
    }
}

class Item {

    private ItemType type;
    private int nb;

    public Item() {}

    public Item(ItemType type, int quantity) {
        this.type = type;
        this.nb = quantity;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}
