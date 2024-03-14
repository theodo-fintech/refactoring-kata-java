package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

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
}
