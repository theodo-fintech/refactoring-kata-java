package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.data.requests.ItemRequest;
import com.sipios.refactoring.data.requests.ShoppingRequest;
import com.sipios.refactoring.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.lang.Double.parseDouble;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ShoppingControllerTests extends UnitTest {

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private ShoppingController controller;

    @Test
    void getPrice_whenEmptyCart_shouldReturnZero() {
        // Given
        var emptyCart = new ItemRequest[] {};
        // When
        var actual = controller.getPrice(new ShoppingRequest(emptyCart, "STANDARD_CUSTOMER"));
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
        ItemRequest[] items = {new ItemRequest(itemType, 1)};
        // When
        var actual = controller.getPrice(new ShoppingRequest(items, customerType));
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
        ItemRequest[] items = {new ItemRequest(itemType, 1)};
        var expected = 30 * 0.9;
        // When
        var actual = controller.getPrice(new ShoppingRequest(items, customerType));
        // Then
        assertEquals(expected, parseDouble(actual));
    }

}
