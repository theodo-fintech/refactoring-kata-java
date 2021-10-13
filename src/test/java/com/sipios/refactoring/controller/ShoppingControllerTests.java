package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.web.server.ResponseStatusException;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void should_throw() {
        Assertions.assertThrows(
            ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {}, ""))
        );
    }

    @Test
    void testStandardCustomer() {
        Assertions.assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void testPremiumCustomer() {
        Assertions.assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, "PREMIUM_CUSTOMER"))
        );
    }

    @Test
    void testPlatiniumCustomer() {
        Assertions.assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, "PLATINUM_CUSTOMER"))
        );
    }

    @Test
    void testStandardCustomerTeeShirt() {
        Assertions.assertEquals("30.0",
            controller.getPrice(new Body(new Item[] {new Item("TSHIRT", 1)}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void testStandardCustomerDress() {
        Assertions.assertEquals("50.0",
            controller.getPrice(new Body(new Item[] {new Item("DRESS", 1)}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void testStandardCustomerJacket() {
        Assertions.assertEquals("100.0",
            controller.getPrice(new Body(new Item[] {new Item("JACKET", 1)}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void testStandardCustomerALotOfProducts() {
        Assertions.assertEquals("180.0",
            controller.getPrice(new Body(new Item[] {new Item ("TSHIRT", 1), new Item("DRESS", 1), new Item("JACKET", 1)}, "STANDARD_CUSTOMER"))
        );
    }
}
