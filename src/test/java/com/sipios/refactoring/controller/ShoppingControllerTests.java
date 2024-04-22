package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.dtos.Purchase;
import com.sipios.refactoring.dtos.PurchaseItem;
import com.sipios.refactoring.services.ShoppingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShoppingControllerTests extends UnitTest {

    @Mock
    ShoppingService shoppingService;

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        when(shoppingService.getPriceByDate(any(), any(), any())).thenReturn(100d);
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Purchase(new PurchaseItem[]{}, "STANDARD_CUSTOMER"), mock(BindingResult.class))
        );
    }


}
