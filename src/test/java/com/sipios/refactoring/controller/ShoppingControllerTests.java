package com.sipios.refactoring.controller;

import com.sipios.refactoring.CustomerType;
import com.sipios.refactoring.ItemType;
import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.customer.Customer;
import com.sipios.refactoring.customer.PlatiniumCustomer;
import com.sipios.refactoring.customer.PremiumCustomer;
import com.sipios.refactoring.customer.StandardCustomer;
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
            () -> controller.getPrice(new Body(new Item[] {}, new StandardCustomer()))
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
            controller.getPrice(new Body(new Item[] {}, new StandardCustomer()))
        );
    }

    @Test
    void testPremiumCustomer() {
        Assertions.assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, new PremiumCustomer()))
        );
    }

    @Test
    void testPlatiniumCustomer() {
        Assertions.assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, new PlatiniumCustomer()))
        );
    }

    @Test
    void testStandardCustomerTeeShirt() {
        Assertions.assertEquals("30.0",
            controller.getPrice(new Body(new Item[] {new Item(ItemType.TSHIRT, 1)}, new StandardCustomer()))
        );
    }

    @Test
    void testStandardCustomerDress() {
        Assertions.assertEquals("50.0",
            controller.getPrice(new Body(new Item[] {new Item(ItemType.DRESS, 1)}, new StandardCustomer()))
        );
    }

    @Test
    void testStandardCustomerJacket() {
        Assertions.assertEquals("100.0",
            controller.getPrice(new Body(new Item[] {new Item(ItemType.JACKET, 1)}, new StandardCustomer()))
        );
    }

    @Test
    void testStandardCustomerALotOfProducts() {
        Assertions.assertEquals("180.0",
            controller.getPrice(new Body(new Item[] {new Item (ItemType.TSHIRT, 1), new Item(ItemType.DRESS, 1), new Item(ItemType.JACKET, 1)}, new StandardCustomer()))
        );
    }
}
