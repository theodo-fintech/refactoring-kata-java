package com.sipios.refactoring.service.impl;

import com.sipios.refactoring.data.domain.Item;
import com.sipios.refactoring.data.domain.ShoppingCart;
import com.sipios.refactoring.service.DiscountService;
import com.sipios.refactoring.service.ShoppingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * A {@code ShoppingService} is...
 * TODO: write javadoc...
 */
@Service
public class ShoppingServiceImpl implements ShoppingService {

    private final DiscountService discountService;

    public ShoppingServiceImpl(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Override
    public double getPrice(ShoppingCart cart, LocalDate date) {
        double price = 0;
        var applyDiscount = discountService.isSeasonal(date);
        for (Item item : cart.getItems()) {
            var type = item.getType();
            var discount = applyDiscount ? type.getSeasonalDiscount() : 1;
            price += type.getPrice() * item.getQuantity() * cart.getCustomerType().getDiscount() * discount;
        }
        return price;
    }

}
