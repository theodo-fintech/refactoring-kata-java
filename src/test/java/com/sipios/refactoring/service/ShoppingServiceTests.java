package com.sipios.refactoring.service;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.data.domain.CustomerType;
import com.sipios.refactoring.data.domain.Item;
import com.sipios.refactoring.data.domain.ItemType;
import com.sipios.refactoring.data.domain.ShoppingCart;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A {@code ShoppingServiceTests} is...
 * TODO: write javadoc...
 */
public class ShoppingServiceTests extends UnitTest {


    public ShoppingService shoppingService;

    @Test
    public void getPrice_whenOneTshirtAndSeasonalDiscount_shouldReturnOneTshirtPriceWithSeasonalDiscount() {
        // Given
        var summerDate = LocalDate.parse("2023-06-10");
        var itemType = ItemType.TSHIRT;
        var items = List.of(new Item(itemType, 1));
        var shoppingCart = new ShoppingCart(items, CustomerType.STANDARD);
        var expected = itemType.getPrice() * itemType.getSeasonalDiscount();
        // When
        var actual = shoppingService.getPrice(shoppingCart, summerDate);
        // Then
        assertEquals(expected, actual);
    }

}
