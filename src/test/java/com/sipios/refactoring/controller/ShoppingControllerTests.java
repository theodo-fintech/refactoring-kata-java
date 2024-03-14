package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingControllerTests extends UnitTest {

    private final ShoppingController controller = new TestableShoppingController(true);

    static class TestableShoppingController extends ShoppingController {

        private final boolean applyDiscounts;

        TestableShoppingController(boolean applyDiscounts) {
            this.applyDiscounts = applyDiscounts;
        }

        @Override
        protected boolean isNotWinterOrSummerDiscountsPeriods() {
            return !applyDiscounts;
        }
    }

    @Test
    void should_throw_when_customer_type_is_unknown() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {}, "UNKNOWN"))
        );
    }

    @Test
    void should_return_zero_when_body_items_is_null() {
        var body = new Body(null, "STANDARD_CUSTOMER");
        var price = controller.getPrice(body);
        assertThat(price).isEqualTo("0");
    }

    @Test
    void should_return_zero_when_items_are_not_found() {
        var items = new Item[]{new Item("UNKNOWN", 1)};
        var body = new Body(items, "STANDARD_CUSTOMER");
        var price = controller.getPrice(body);
        assertThat(price).isEqualTo("0.0");
    }

    public static Stream<Arguments> totalPriceCalculationPerItemAndPerCustomerTypeTestCases() {
        return Stream.of(
            // Regular period
            Arguments.of("STANDARD_CUSTOMER", "TSHIRT", 3, "90.0", false),
            Arguments.of("STANDARD_CUSTOMER", "DRESS", 1, "50.0", false),
            Arguments.of("STANDARD_CUSTOMER", "JACKET", 2, "200.0", false),

            Arguments.of("PREMIUM_CUSTOMER", "TSHIRT", 3, "81.0", false),
            Arguments.of("PREMIUM_CUSTOMER", "DRESS", 1, "45.0", false),
            Arguments.of("PREMIUM_CUSTOMER", "JACKET", 2, "180.0", false),

            Arguments.of("PLATINUM_CUSTOMER", "TSHIRT", 3, "45.0", false),
            Arguments.of("PLATINUM_CUSTOMER", "DRESS", 1, "25.0", false),
            Arguments.of("PLATINUM_CUSTOMER", "JACKET", 2, "100.0", false),


            // Discount period
            Arguments.of("STANDARD_CUSTOMER", "TSHIRT", 3, "90.0", true),
            Arguments.of("STANDARD_CUSTOMER", "DRESS", 1, "40.0", true),
            Arguments.of("STANDARD_CUSTOMER", "JACKET", 2, "180.0", true),

            Arguments.of("PREMIUM_CUSTOMER", "TSHIRT", 3, "81.0", true),
            Arguments.of("PREMIUM_CUSTOMER", "DRESS", 1, "36.0", true),
            Arguments.of("PREMIUM_CUSTOMER", "JACKET", 2, "162.0", true),

            Arguments.of("PLATINUM_CUSTOMER", "TSHIRT", 3, "45.0", true),
            Arguments.of("PLATINUM_CUSTOMER", "DRESS", 1, "20.0", true),
            Arguments.of("PLATINUM_CUSTOMER", "JACKET", 2, "90.0", true)
        );
    }

    @ParameterizedTest
    @MethodSource("totalPriceCalculationPerItemAndPerCustomerTypeTestCases")
    void should_calculate_total_price_per_item_type_per_customer_type(String customerType,
                                                                      String itemType,
                                                                      int quantity,
                                                                      String expectedPrice,
                                                                      boolean isDiscountPeriod) {
        var testableController = new TestableShoppingController(isDiscountPeriod);
        var items = new Item[]{new Item(itemType, quantity)};
        var body = new Body(items, customerType);
        var price = testableController.getPrice(body);
        assertThat(price).isEqualTo(expectedPrice);
    }
}
