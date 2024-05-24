package com.sipios.refactoring.service.impl;

import com.sipios.refactoring.data.domain.ShoppingCart;
import com.sipios.refactoring.service.ShoppingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * A {@code ShoppingService} is...
 * TODO: write javadoc...
 */
@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Override
    public double getPrice(ShoppingCart cart, LocalDate date) {
        // TODO: impl
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
