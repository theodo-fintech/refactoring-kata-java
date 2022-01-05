package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.dto.ShoppingDetails;
import com.sipios.refactoring.dto.ShoppingItem;
import com.sipios.refactoring.utils.enums.CustomerType;
import com.sipios.refactoring.utils.enums.ShoppingItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new ShoppingDetails(new ShoppingItem[] {}, CustomerType.STANDARD_CUSTOMER))
        );

        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new ShoppingDetails(new ShoppingItem[] {}, CustomerType.PREMIUM_CUSTOMER))
        );

        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new ShoppingDetails(new ShoppingItem[] {}, CustomerType.PLATINUM_CUSTOMER))
        );

        ShoppingItem tshirts = new ShoppingItem(ShoppingItemType.TSHIRT, 3);
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new ShoppingDetails( new ShoppingItem[] {tshirts}, CustomerType.PLATINUM_CUSTOMER))
        );

    }
}
