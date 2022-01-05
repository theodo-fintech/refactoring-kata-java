package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.dto.ShoppingDetails;
import com.sipios.refactoring.dto.ShoppingItem;
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
            () -> controller.getPrice(new ShoppingDetails(new ShoppingItem[] {}, "STANDARD_CUSTOMER"))
        );

        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new ShoppingDetails(new ShoppingItem[] {}, "PREMIUM_CUSTOMER"))
        );

        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new ShoppingDetails(new ShoppingItem[] {}, "PLATINUM_CUSTOMER"))
        );

        ShoppingItem tshirts = new ShoppingItem("TSHIRT", 3);
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new ShoppingDetails( new ShoppingItem[] {tshirts}, "PLATINUM_CUSTOMER"))
        );

        ShoppingItem invalidItems = new ShoppingItem("INVALID ITEM", 1);
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new ShoppingDetails( new ShoppingItem[] {invalidItems}, "PLATINUM_CUSTOMER"))
        );
    }

    @Test
    void should_throw() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new ShoppingDetails(new ShoppingItem[] {}, "INVALID_CUSTOMER"))
        );
    }

}
