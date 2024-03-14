package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

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
}
