package com.sipios.refactoring.controller;

import java.util.Date;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class ShoppingControllerTests extends UnitTest {

    @Mock
    private DateTime mockDateTime;

    @InjectMocks
    private ShoppingController controller;

    @Test
    void standard_customer_with_no_item_should_return_0() {

        Mockito.when(mockDateTime.getDate()).thenReturn(new Date(0));

        Assertions.assertEquals(
            0.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER")))
        );
    }
}
