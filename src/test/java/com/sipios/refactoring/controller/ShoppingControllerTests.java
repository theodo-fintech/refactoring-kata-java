package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShoppingControllerTests extends UnitTest {

    private ShoppingController controller;

    @BeforeEach
    void init() {
        controller = new ShoppingController(Clock.systemDefaultZone());
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
            Arguments.of("STANDARD_CUSTOMER", "TSHIRT", 3, "90.0"),
            Arguments.of("STANDARD_CUSTOMER", "DRESS", 1, "50.0"),
            Arguments.of("STANDARD_CUSTOMER", "JACKET", 2, "200.0"),

            Arguments.of("PREMIUM_CUSTOMER", "TSHIRT", 3, "81.0"),
            Arguments.of("PREMIUM_CUSTOMER", "DRESS", 1, "45.0"),
            Arguments.of("PREMIUM_CUSTOMER", "JACKET", 2, "180.0"),

            Arguments.of("PLATINUM_CUSTOMER", "TSHIRT", 3, "45.0"),
            Arguments.of("PLATINUM_CUSTOMER", "DRESS", 1, "25.0"),
            Arguments.of("PLATINUM_CUSTOMER", "JACKET", 2, "100.0")
        );
    }

    @ParameterizedTest
    @MethodSource("totalPriceCalculationPerItemAndPerCustomerTypeTestCases")
    void should_calculate_total_price_per_item_type_per_customer_type(String customerType,
                                                                      String itemType,
                                                                      int quantity,
                                                                      String expectedPrice) {
        var items = new Item[]{new Item(itemType, quantity)};
        var body = new Body(items, customerType);
        var price = controller.getPrice(body);
        assertThat(price).isEqualTo(expectedPrice);
    }

    public static Stream<Arguments> totalPriceThresholdExceededPerCustomerTypeTestCases() {
        return Stream.of(
            Arguments.of("STANDARD_CUSTOMER", "JACKET", 3, "Price (300.0) is too high for standard customer"),
            Arguments.of("PREMIUM_CUSTOMER", "JACKET", 10, "Price (900.0) is too high for premium customer"),
            Arguments.of("PLATINUM_CUSTOMER", "JACKET", 50, "Price (2500.0) is too high for platinum customer")
        );
    }

    @ParameterizedTest
    @MethodSource("totalPriceThresholdExceededPerCustomerTypeTestCases")
    void should_throw_when_total_price_per_customer_type_threshold_is_exceeded(String customerType,
                                                                               String itemType,
                                                                               int quantity,
                                                                               String expectedMessage) {
        assertThatThrownBy(() -> {
            var items = new Item[]{new Item(itemType, quantity)};
            var body = new Body(items, customerType);
            controller.getPrice(body);
        })
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining(expectedMessage);
    }


    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ComputePriceInDiscountPeriod {

        private ShoppingController shoppingControllerInDiscountPeriod;

        @BeforeEach
        void init() {
            var clock = Clock.fixed(Instant.parse("2024-01-10T10:15:30.00Z"),
                ZoneId.of("Europe/Paris"));

            shoppingControllerInDiscountPeriod = new ShoppingController(clock);
        }

        public Stream<Arguments> totalPriceCalculationPerItemAndPerCustomerTypeTestCases() {
            return Stream.of(
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
        void should_calculate_total_price_per_item_type_per_customer_type_in_discount_period(String customerType,
                                                                          String itemType,
                                                                          int quantity,
                                                                          String expectedPrice) {
            var items = new Item[]{new Item(itemType, quantity)};
            var body = new Body(items, customerType);
            var price = shoppingControllerInDiscountPeriod.getPrice(body);
            assertThat(price).isEqualTo(expectedPrice);
        }
    }
}
