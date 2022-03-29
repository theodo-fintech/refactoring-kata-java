package com.sipios.refactoring.controller;

import com.sipios.refactoring.entity.*;
import com.sipios.refactoring.exception.HighPriceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.sipios.refactoring.entity.ConsumerType.*;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);
    private final Map<ProductType, Price> PRODUCTS_STANDARD = Map.of(ProductType.TSHIRT, new Price(30, 1),
                                                            ProductType.DRESS, new Price(50, 0.8),
                                                            ProductType.JACKET, new Price(100, 0.9));
    private final Map<ConsumerType, Price> CONSUMERS_STANDARD = Map.of(STANDARD_CUSTOMER, new Price(200, 1),
                                                                PREMIUM_CUSTOMER, new Price(800, 0.9),
                                                                PLATINUM_CUSTOMER, new Price(2000, 0.5));
    private final String TIME_ZONE = "Europe/Paris";

    @PostMapping
    public double getPrice(@RequestBody Shopping shopping) {
        if (Objects.isNull(shopping.getItems())) {
            LOGGER.info("No items found");
            return 0;
        }

        double price = getTotalPrice(shopping);

        checkPrice(price, shopping.getType());

        return price;
    }

    /**
     * Return an exception if the price is higher than max price for the consumer type
     *
     * @param price        total price
     * @param consumerType consumer type
     */
    private void checkPrice(double price, ConsumerType consumerType) {
        if (price > CONSUMERS_STANDARD.get(consumerType).getBasePrice()) {
            throw new HighPriceException(price, consumerType);
        }
    }

    /**
     * Calculate the total price with discounts
     *
     * @param shopping
     * @return amount to be paid
     */
    private double getTotalPrice(Shopping shopping) {

        double discount = getDiscountByConsumerType(shopping.getType());
        boolean isDiscountPeriod = isDiscountPeriod();

        double price = 0;
        for (Item item : shopping.getItems()) {
            price += getPricePerItem(item.getType(), PRODUCTS_STANDARD.get(item.getType()), item.getNb(), isDiscountPeriod, discount);
        }
        LOGGER.info(String.format("Total price : %s", price));
        return price;

    }

    /**
     * Get the price per item. If it's a discount period, add a percentage discount
     *
     * @param cost
     * @param amount
     * @param isDiscountPeriod
     * @param discount
     * @return
     */
    private double getPricePerItem(ProductType productType, Price cost, int amount, boolean isDiscountPeriod, double discount) {
        double additionalPercentage = isDiscountPeriod ? cost.getPercentageDiscount() : 1;
        double price = cost.getBasePrice() * amount * additionalPercentage * discount;
        LOGGER.info(String.format("Product : %s * %d : %s", productType, amount, price));
        return price;
    }

    /**
     * Indicate if it's a discount period
     *
     * @return true if it's between June 5th and June 15th OR between January 5th and January 15th
     */
    private boolean isDiscountPeriod() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTime(date);

        // if we are in winter or summer discounts periods
        return calendar.get(Calendar.MONTH) == Calendar.JUNE && calendar.get(Calendar.DAY_OF_MONTH) < 15 && calendar.get(Calendar.DAY_OF_MONTH) > 5
            || calendar.get(Calendar.MONTH) == Calendar.JANUARY && calendar.get(Calendar.DAY_OF_MONTH) < 15 && calendar.get(Calendar.DAY_OF_MONTH) > 5;

    }

    private double getDiscountByConsumerType(ConsumerType consumerType) {
        double discount = CONSUMERS_STANDARD.get(consumerType).getPercentageDiscount();
        LOGGER.info(String.format("Discount of %s for consumer %s", discount, consumerType.toString()));
        return discount;
    }
}

