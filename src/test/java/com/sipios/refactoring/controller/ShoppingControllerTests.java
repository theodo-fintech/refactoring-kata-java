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
    void should_throw_customer_type_unknown() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {}, ""))
        );

        Assertions.assertEquals(exception.getMessage(), "400 BAD_REQUEST \"Cannot identify the customer type\"");
    }

    @Test
    void should_throw_customer_type_null() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {}, null))
        );

        Assertions.assertEquals(exception.getMessage(), "400 BAD_REQUEST \"Cannot identify the customer type\"");
    }

    @Test
    void should_price_be_zero() {
        var price = controller.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER"));
        Assertions.assertEquals("0.0", price);
    }

    @Test
    void should_price_for_standard_customer_is_too_high() {
        Item tshirt = new Item("TSHIRT",3);
        Item dress = new Item("DRESS",1);
        Item jacket = new Item("JACKET",2);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, "STANDARD_CUSTOMER"))
        );
        
        Assertions.assertEquals(exception.getMessage(), "400 BAD_REQUEST \"Price (340.0) is too high for standard customer\"");
    }

    @Test
    void should_price_be_correct_for_standard_customer() {
        Item tshirt = new Item("TSHIRT",1);
        Item dress = new Item("DRESS",1);
        Item jacket = new Item("JACKET",1);

        var price = controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, "STANDARD_CUSTOMER"));
        Assertions.assertEquals("180.0", price);
    }

    @Test
    void should_price_for_premium_customer_is_too_high() {
        Item tshirt = new Item("TSHIRT",3);
        Item dress = new Item("DRESS",1);
        Item jacket = new Item("JACKET",8);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, "PREMIUM_CUSTOMER"))
        );
        
        Assertions.assertEquals(exception.getMessage(), "400 BAD_REQUEST \"Price (846.0) is too high for premium customer\"");
    }

    @Test
    void should_price_be_correct_for_premium_customer() {
        Item tshirt = new Item("TSHIRT",3);
        Item dress = new Item("DRESS",1);
        Item jacket = new Item("JACKET",2);

        var price = controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, "PREMIUM_CUSTOMER"));
        Assertions.assertEquals("306.0", price);
    }

    @Test
    void should_price_for_premium_platinum_is_too_high() {
        Item tshirt = new Item("TSHIRT",10);
        Item dress = new Item("DRESS",3);
        Item jacket = new Item("JACKET",50);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, "PLATINUM_CUSTOMER"))
        );
        
        Assertions.assertEquals(exception.getMessage(), "400 BAD_REQUEST \"Price (2725.0) is too high for platinum customer\"");
    }

    @Test
    void should_price_be_correct_for_platinum_customer() {
        Item tshirt = new Item("TSHIRT",10);
        Item dress = new Item("DRESS",3);
        Item jacket = new Item("JACKET",20);

        var price = controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, "PLATINUM_CUSTOMER"));
        Assertions.assertEquals("1225.0", price);
    }
}