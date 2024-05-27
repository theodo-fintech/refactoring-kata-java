package com.sipios.refactoring.service;

import com.sipios.refactoring.data.domain.ShoppingCart;

import java.time.LocalDate;

/**
 * A {@code ShoppingService} is...
 * TODO: write javadoc...
 */
public interface ShoppingService {

    /**
     * Computes total amount depending on the customer type, the types
     * and quantity of product and if we are in winter or summer discounts
     * periods
     *
     * @param cart
     * @param date
     * @return
     */
    double getPrice(ShoppingCart cart, LocalDate date);

}
