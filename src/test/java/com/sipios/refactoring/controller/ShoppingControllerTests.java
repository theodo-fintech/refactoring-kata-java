package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.model.ShoppingCart;
import com.sipios.refactoring.model.CustomerType;
import com.sipios.refactoring.model.ItemQuantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class ShoppingControllerTests extends UnitTest {
    ItemQuantity oneTShirt = new ItemQuantity("TSHIRT", 1);
    ItemQuantity oneDress = new ItemQuantity("DRESS", 1);
    ItemQuantity oneJacket = new ItemQuantity("JACKET", 1);

    @InjectMocks
    private ShoppingController controller;

    @Test
    void check_price_for_empty_shopping_cart() {
        Assertions.assertEquals("0.0", controller.getPrice(new ShoppingCart(new ItemQuantity[]{}, CustomerType.STANDARD_CUSTOMER)));
    }

    @Test
    void check_one_tShirt_price_for_standard_customer() {
        ShoppingCart cart = new ShoppingCart(new ItemQuantity[]{oneTShirt}, CustomerType.STANDARD_CUSTOMER);
        Assertions.assertEquals("30.0",controller.getPrice(cart));
    }

    @Test
    void check_one_dress_price_for_standard_customer() {
        ShoppingCart cart = new ShoppingCart(new ItemQuantity[]{oneDress}, CustomerType.STANDARD_CUSTOMER);
        Assertions.assertEquals("50.0",controller.getPrice(cart));
    }

    @Test
    void check_one_jacket_price_for_standard_customer() {
        ShoppingCart cart = new ShoppingCart(new ItemQuantity[]{oneJacket}, CustomerType.STANDARD_CUSTOMER);
        Assertions.assertEquals("100.0",controller.getPrice(cart));
    }

    @Test
    void check_one_tShirt_price_for_premium_customer() {
        ShoppingCart cart = new ShoppingCart(new ItemQuantity[]{oneTShirt},  CustomerType.PREMIUM_CUSTOMER);
        Assertions.assertEquals("27.0",controller.getPrice(cart));
    }

    @Test
    void check_one_dress_price_for_premium_customer() {
        ShoppingCart cart = new ShoppingCart(new ItemQuantity[]{oneDress},  CustomerType.PREMIUM_CUSTOMER);
        Assertions.assertEquals("45.0",controller.getPrice(cart));
    }

    @Test
    void check_one_jacket_price_for_premium_customer() {
        ShoppingCart cart = new ShoppingCart(new ItemQuantity[]{oneJacket},  CustomerType.PREMIUM_CUSTOMER);
        Assertions.assertEquals("90.0",controller.getPrice(cart));
    }

    @Test
    void check_one_tShirt_price_for_platinum_customer() {
        ShoppingCart cart = new ShoppingCart(new ItemQuantity[]{oneTShirt},  CustomerType.PLATINUM_CUSTOMER);
        Assertions.assertEquals("15.0",controller.getPrice(cart));
    }

    @Test
    void check_one_dress_price_for_platinum_customer() {
        ShoppingCart cart = new ShoppingCart(new ItemQuantity[]{oneDress},  CustomerType.PLATINUM_CUSTOMER);
        Assertions.assertEquals("25.0",controller.getPrice(cart));
    }

    @Test
    void check_one_jacket_price_for_platinum_customer() {
        ShoppingCart cart = new ShoppingCart(new ItemQuantity[]{oneJacket},  CustomerType.PLATINUM_CUSTOMER);
        Assertions.assertEquals("50.0",controller.getPrice(cart));
    }
}
