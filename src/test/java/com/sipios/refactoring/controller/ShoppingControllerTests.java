package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static java.lang.Double.parseDouble;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void getPrice_whenEmptyCart_shouldReturnZero() {
        // Given
        var emptyCart = new Item[] {};
        // When
        var actual = controller.getPrice(new ShoppingCart(emptyCart, "STANDARD_CUSTOMER"));
        // Then
        assertEquals(0, parseDouble(actual));
    }

    // TODO: move this unit test to the service layer once the new API is implemented.
    //  rationale:
    //  this test is not deterministic since getPrice results depends on the current date.
    //  We have to design the new API to make ShoppingController testable via dependency injection
    //  So the sole purpose of this test is to catch potential regressions during early refactoring
    //  and to be recycled later as a proper service layer unit test
    @Test
    public void getPrice_whenOneTshirt_shouldReturnOneTshirtUnitPrice() {
        // Given
        var itemType = "TSHIRT";
        var customerType = "STANDARD_CUSTOMER";
        Item[] items = {new Item(itemType, 1)};
        // When
        var actual = controller.getPrice(new ShoppingCart(items, customerType));
        // Then
        assertEquals(30, parseDouble(actual));
    }

    // TODO: move this unit test to the service layer once the new API is implemented.
    //  rationale:
    //  this test is not deterministic since getPrice results depends on the current date.
    //  We have to design the new API to make ShoppingController testable via dependency injection
    //  So the sole purpose of this test is to catch potential regressions during early refactoring
    //  and to be recycled later as a proper service layer unit test
    @Test
    public void getPrice_whenOneTshirtAndPremiumDiscount_shouldReturnOneTshirtPriceWithPremiumDiscount() {
        // Given
        var customerType = "PREMIUM_CUSTOMER";
        var itemType = "TSHIRT";
        Item[] items = {new Item(itemType, 1)};
        var expected = 30 * 0.9;
        // When
        var actual = controller.getPrice(new ShoppingCart(items, customerType));
        // Then
        assertEquals(expected, parseDouble(actual));
    }

}
