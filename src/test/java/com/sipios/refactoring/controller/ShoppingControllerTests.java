package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.web.server.ResponseStatusException;

class ShoppingControllerTests extends UnitTest {
    Item oneTShirt = new Item("TSHIRT", 1);
    Item oneDress = new Item("DRESS", 1);
    Item oneJacket = new Item("JACKET", 1);

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_throw_ResponseStatusException_for_invalid_customer() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[]{}, "INVALID_CUSTOMER"))
        );
    }

    @Test
    void check_price_for_empty_shopping_cart() {
        Assertions.assertEquals("0.0", controller.getPrice(new Body(new Item[]{}, "STANDARD_CUSTOMER")));
    }

    @Test
    void check_one_tShirt_price_for_standard_customer() {
        Body cart = new Body(new Item[]{oneTShirt}, "STANDARD_CUSTOMER");
        Assertions.assertEquals("30.0",controller.getPrice(cart));
    }

    @Test
    void check_one_dress_price_for_standard_customer() {
        Body cart = new Body(new Item[]{oneDress}, "STANDARD_CUSTOMER");
        Assertions.assertEquals("50.0",controller.getPrice(cart));
    }

    @Test
    void check_one_jacket_price_for_standard_customer() {
        Body cart = new Body(new Item[]{oneJacket}, "STANDARD_CUSTOMER");
        Assertions.assertEquals("100.0",controller.getPrice(cart));
    }

    @Test
    void check_one_tShirt_price_for_premium_customer() {
        Body cart = new Body(new Item[]{oneTShirt}, "PREMIUM_CUSTOMER");
        Assertions.assertEquals("27.0",controller.getPrice(cart));
    }

    @Test
    void check_one_dress_price_for_premium_customer() {
        Body cart = new Body(new Item[]{oneDress}, "PREMIUM_CUSTOMER");
        Assertions.assertEquals("45.0",controller.getPrice(cart));
    }

    @Test
    void check_one_jacket_price_for_premium_customer() {
        Body cart = new Body(new Item[]{oneJacket}, "PREMIUM_CUSTOMER");
        Assertions.assertEquals("90.0",controller.getPrice(cart));
    }

    @Test
    void check_one_tShirt_price_for_platinum_customer() {
        Body cart = new Body(new Item[]{oneTShirt}, "PLATINUM_CUSTOMER");
        Assertions.assertEquals("15.0",controller.getPrice(cart));
    }

    @Test
    void check_one_dress_price_for_platinum_customer() {
        Body cart = new Body(new Item[]{oneDress}, "PLATINUM_CUSTOMER");
        Assertions.assertEquals("25.0",controller.getPrice(cart));
    }

    @Test
    void check_one_jacket_price_for_platinum_customer() {
        Body cart = new Body(new Item[]{oneJacket}, "PLATINUM_CUSTOMER");
        Assertions.assertEquals("50.0",controller.getPrice(cart));
    }
}
