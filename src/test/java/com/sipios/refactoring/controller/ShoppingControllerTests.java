package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.models.dtos.ShoppingCartRequest;
import com.sipios.refactoring.models.dtos.CartItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static com.sipios.refactoring.models.dtos.CustomerType.PREMIUM_CUSTOMER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class ShoppingControllerTests extends UnitTest {

    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new ShoppingCartRequest(new CartItem[] {}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void should_has_elements_throw() {
        CartItem jeans = new CartItem("JEAN",511);
        CartItem dress = new CartItem("DRESS",50);
        CartItem[] cartsItems = new CartItem[2];
        cartsItems[0]= jeans;
        cartsItems[1]= dress;
        ShoppingCartRequest shoppingCartRequest = new ShoppingCartRequest(cartsItems, PREMIUM_CUSTOMER.name());
        String price = controller.getPrice(shoppingCartRequest);

        assertEquals("123",price);
    }
}
