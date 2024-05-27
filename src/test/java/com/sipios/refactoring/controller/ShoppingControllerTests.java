package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.data.requests.ItemRequest;
import com.sipios.refactoring.data.requests.ShoppingRequest;
import com.sipios.refactoring.service.ShoppingService;
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
    private ShoppingService shoppingService;

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

}
