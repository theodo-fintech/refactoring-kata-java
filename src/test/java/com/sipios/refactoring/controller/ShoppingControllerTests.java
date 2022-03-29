package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.entity.ConsumerType;
import com.sipios.refactoring.entity.Shopping;
import com.sipios.refactoring.entity.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Shopping(new Item[] {}, ConsumerType.STANDARD_CUSTOMER))
        );
    }
}
