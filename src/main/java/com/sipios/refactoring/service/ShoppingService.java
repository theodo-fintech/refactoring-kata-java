package com.sipios.refactoring.service;

import com.sipios.refactoring.model.Body;

public interface ShoppingService {

    /**
     * Get the price of items for specific customer
     *
     * @param body
     * @return
     */
    String getPrice(Body body);
}
