package com.sipios.refactoring.service;

import com.sipios.refactoring.model.Body;
import com.sipios.refactoring.model.Item;
import com.sipios.refactoring.service.ShoppingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShopppingServiceTests {

    @InjectMocks()
    ShoppingServiceImpl shopppingService;

    @Test
    void givenStandardCustomerWithTshirt_getPriceOfTshirt_thenSucced() {
        Item tshirt = new Item("TSHIRT", 1);
        Item[] items = {tshirt};
        Body body = new Body(items, "STANDARD_CUSTOMER");


        String price = shopppingService.getPrice(body);
        String expectedPrice = "30.0";
        Assertions.assertEquals(expectedPrice, price);
    }

    @Test
    void givenPremiumCustomerWithJacket_getPriceOfJacket_thenSucced() {
        Item jacket = new Item("JACKET", 1);
        Item[] items = {jacket};
        Body body = new Body(items, "PREMIUM_CUSTOMER");


        String price = shopppingService.getPrice(body);
        String expectedPrice = "90.0";
        Assertions.assertEquals(expectedPrice, price);
    }

    @Test
    void givenPlatinumCustomerWithDress_getPriceOfDress_thenSucced() {
        Item dress = new Item("DRESS", 1);
        Item[] items = {dress};
        Body body = new Body(items, "PLATINUM_CUSTOMER");


        String price = shopppingService.getPrice(body);
        String expectedPrice = "25.0";
        Assertions.assertEquals(expectedPrice, price);
    }
}
