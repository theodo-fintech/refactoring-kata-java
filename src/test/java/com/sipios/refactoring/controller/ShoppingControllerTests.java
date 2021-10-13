package com.sipios.refactoring.controller;

import com.sipios.refactoring.CustomerType;
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
            () -> controller.getPrice(new Body(new Item[] {}, CustomerType.STANDARD))
        );
    }

    /*@Test
    void should_throw() {
        Assertions.assertThrows(
            ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {}, CustomerType))
        );
    }*/

    @Test
    void testStandardCustomer() {
        Assertions.assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, CustomerType.STANDARD))
        );
    }

    @Test
    void testPremiumCustomer() {
        Assertions.assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, CustomerType.PREMIUM))
        );
    }

    @Test
    void testPlatiniumCustomer() {
        Assertions.assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, CustomerType.PLATINIUM))
        );
    }

    @Test
    void testStandardCustomerTeeShirt() {
        Assertions.assertEquals("30.0",
            controller.getPrice(new Body(new Item[] {new Item("TSHIRT", 1)}, CustomerType.STANDARD))
        );
    }

    @Test
    void testStandardCustomerDress() {
        Assertions.assertEquals("50.0",
            controller.getPrice(new Body(new Item[] {new Item("DRESS", 1)}, CustomerType.STANDARD))
        );
    }

    @Test
    void testStandardCustomerJacket() {
        Assertions.assertEquals("100.0",
            controller.getPrice(new Body(new Item[] {new Item("JACKET", 1)}, CustomerType.STANDARD))
        );
    }

    @Test
    void testStandardCustomerALotOfProducts() {
        Assertions.assertEquals("180.0",
            controller.getPrice(new Body(new Item[] {new Item ("TSHIRT", 1), new Item("DRESS", 1), new Item("JACKET", 1)}, CustomerType.STANDARD))
        );
    }
}
