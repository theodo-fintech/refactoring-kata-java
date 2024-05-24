package com.sipios.refactoring.service;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.data.domain.CustomerType;
import com.sipios.refactoring.data.domain.Item;
import com.sipios.refactoring.data.domain.ItemType;
import com.sipios.refactoring.data.domain.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShoppingServiceTests extends UnitTest {

    @Autowired
    public ShoppingService shoppingService;

    @Test
    public void getPrice_whenOneTshirtAndSeasonalDiscount_shouldReturnOneTshirtPriceWithSeasonalDiscount() {
        // Given
        var summerDate = LocalDate.parse("2023-06-10");
        var itemType = ItemType.TSHIRT;
        var itemList = List.of(new Item(itemType, 1));
        var shoppingCart = new ShoppingCart(itemList, CustomerType.STANDARD);
        var expected = itemType.getPrice() * itemType.getSeasonalDiscount();
        // When
        var actual = shoppingService.getPrice(shoppingCart, summerDate);
        // Then
        assertEquals(expected, actual);
    }

}
