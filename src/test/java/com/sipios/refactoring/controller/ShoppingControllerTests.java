package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.models.CalculatePriceRequest;
import com.sipios.refactoring.models.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static com.sipios.refactoring.models.CalculatePriceRequestType.STANDARD_CUSTOMER;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new CalculatePriceRequest(new Item[] {}, STANDARD_CUSTOMER))
        );
    }
}
