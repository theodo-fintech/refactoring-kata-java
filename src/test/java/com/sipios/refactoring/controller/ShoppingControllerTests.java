package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.article.DressArticle;
import com.sipios.refactoring.article.JacketArticle;
import com.sipios.refactoring.article.TshirtArticle;
import com.sipios.refactoring.customer.PlatiniumCustomer;
import com.sipios.refactoring.customer.PremiumCustomer;
import com.sipios.refactoring.customer.StandardCustomer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

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
            controller.getPrice(new Body(new Item[] {new Item(new TshirtArticle(), 1)}, new StandardCustomer()))
        );
    }

    @Test
    void testStandardCustomerDress() {
        Assertions.assertEquals("50.0",
            controller.getPrice(new Body(new Item[] {new Item(new DressArticle(), 1)}, new StandardCustomer()))
        );
    }

    @Test
    void testStandardCustomerJacket() {
        Assertions.assertEquals("100.0",
            controller.getPrice(new Body(new Item[] {new Item(new JacketArticle(), 1)}, new StandardCustomer()))
        );
    }

    @Test
    void testStandardCustomerALotOfProducts() {
        Assertions.assertEquals("180.0",
            controller.getPrice(new Body(new Item[] {new Item (new TshirtArticle(), 1), new Item(new DressArticle(), 1), new Item(new JacketArticle(), 1)}, new StandardCustomer()))
        );
    }

    @Test
    void testCalculatePriceStandardCustomerTshirtNotSeasonal() {
        Assertions.assertEquals(30, controller.calculatePrice(new Body(new Item[] {new Item(new TshirtArticle(), 1)}, new StandardCustomer()), false));
    }
    @Test
    void testCalculatePriceStandardCustomerTshirtSeasonal() {
        Assertions.assertEquals(30, controller.calculatePrice(new Body(new Item[] {new Item(new TshirtArticle(), 1)}, new StandardCustomer()), true));
    }
    @Test
    void testCalculatePricePremiumCustomerTshirtNotSeasonal() {
        Assertions.assertEquals(27, controller.calculatePrice(new Body(new Item[] {new Item(new TshirtArticle(), 1)}, new PremiumCustomer()), false));
    }
    @Test
    void testCalculatePricePremiumCustomerTshirtSeasonal() {
        Assertions.assertEquals(27, controller.calculatePrice(new Body(new Item[] {new Item(new TshirtArticle(), 1)}, new PremiumCustomer()), true));
    }
    @Test
    void testCalculatePricePlatiniumCustomerTshirtNotSeasonal() {
        Assertions.assertEquals(15, controller.calculatePrice(new Body(new Item[] {new Item(new TshirtArticle(), 1)}, new PlatiniumCustomer()), false));
    }
    @Test
    void testCalculatePricePlatiniumCustomerTshirtSeasonal() {
        Assertions.assertEquals(15, controller.calculatePrice(new Body(new Item[] {new Item(new TshirtArticle(), 1)}, new PlatiniumCustomer()), true));
    }
    @Test
    void testCalculatePriceStandardCustomerJacketNotSeasonal() {
        Assertions.assertEquals(100, controller.calculatePrice(new Body(new Item[] {new Item(new JacketArticle(), 1)}, new StandardCustomer()), false));
    }
    @Test
    void testCalculatePriceStandardCustomerJacketSeasonal() {
        Assertions.assertEquals(90, controller.calculatePrice(new Body(new Item[] {new Item(new JacketArticle(), 1)}, new StandardCustomer()), true));
    }
    @Test
    void testCalculatePricePremiumCustomerJacketNotSeasonal() {
        Assertions.assertEquals(90, controller.calculatePrice(new Body(new Item[] {new Item(new JacketArticle(), 1)}, new PremiumCustomer()), false));
    }
    @Test
    void testCalculatePricePremiumCustomerJacketSeasonal() {
        Assertions.assertEquals(81, controller.calculatePrice(new Body(new Item[] {new Item(new JacketArticle(), 1)}, new PremiumCustomer()), true));
    }
    @Test
    void testCalculatePricePlatiniumCustomerJacketNotSeasonal() {
        Assertions.assertEquals(50, controller.calculatePrice(new Body(new Item[] {new Item(new JacketArticle(), 1)}, new PlatiniumCustomer()), false));
    }
    @Test
    void testCalculatePricePlatiniumCustomerJacketSeasonal() {
        Assertions.assertEquals(45, controller.calculatePrice(new Body(new Item[] {new Item(new JacketArticle(), 1)}, new PlatiniumCustomer()), true));
    }
    @Test
    void testCalculatePriceStandardCustomerDressNotSeasonal() {
        Assertions.assertEquals(50, controller.calculatePrice(new Body(new Item[] {new Item(new DressArticle(), 1)}, new StandardCustomer()), false));
    }
    @Test
    void testCalculatePriceStandardCustomerDressSeasonal() {
        Assertions.assertEquals(40, controller.calculatePrice(new Body(new Item[] {new Item(new DressArticle(), 1)}, new StandardCustomer()), true));
    }
    @Test
    void testCalculatePricePremiumCustomerDressNotSeasonal() {
        Assertions.assertEquals(45, controller.calculatePrice(new Body(new Item[] {new Item(new DressArticle(), 1)}, new PremiumCustomer()), false));
    }
    @Test
    void testCalculatePricePremiumCustomerDressSeasonal() {
        Assertions.assertEquals(36, controller.calculatePrice(new Body(new Item[] {new Item(new DressArticle(), 1)}, new PremiumCustomer()), true));
    }
    @Test
    void testCalculatePricePlatiniumCustomerDressNotSeasonal() {
        Assertions.assertEquals(25, controller.calculatePrice(new Body(new Item[] {new Item(new DressArticle(), 1)}, new PlatiniumCustomer()), false));
    }
    @Test
    void testCalculatePricePlatiniumCustomerDressSeasonal() {
        Assertions.assertEquals(20, controller.calculatePrice(new Body(new Item[] {new Item(new DressArticle(), 1)}, new PlatiniumCustomer()), true));
    }
}
