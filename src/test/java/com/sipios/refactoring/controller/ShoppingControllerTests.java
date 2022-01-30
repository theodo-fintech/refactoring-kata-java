package com.sipios.refactoring.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class ShoppingControllerTests extends UnitTest {

    @Mock
    private DateTime mockDateTime;

    @InjectMocks
    private ShoppingController controller;

    private static Item oneTshirt;
    private static Item oneDress;
    private static Item oneJacket;

    private static Item tenTshirts;
    private static Item tenDresses;
    private static Item tenJackets;

    @BeforeAll
    public static void setUp(){

        oneTshirt = new Item(ItemType.TSHIRT, 1);
        oneDress = new Item(ItemType.DRESS, 1);
        oneJacket = new Item(ItemType.JACKET, 1);
        tenTshirts = new Item(ItemType.TSHIRT, 10);
        tenDresses = new Item(ItemType.DRESS, 10);
        tenJackets = new Item(ItemType.JACKET, 10);
    }


    @Test
    void customer_with_no_item_should_return_0() {

        Mockito.when(mockDateTime.getDate()).thenReturn(new Date(0));

        Assertions.assertEquals(
            0.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {}, CustomerType.STANDARD_CUSTOMER)))
        );
        Assertions.assertEquals(
            0.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {}, CustomerType.PREMIUM_CUSTOMER)))
        );
        Assertions.assertEquals(
            0.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {}, CustomerType.PLATINUM_CUSTOMER)))
        );
    }

    @Test
    void different_customer_types_get_different_discount() {

        Mockito.when(mockDateTime.getDate()).thenReturn(new Date(0));

        Assertions.assertEquals(
            30.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {oneTshirt}, CustomerType.STANDARD_CUSTOMER)))
        );
        Assertions.assertEquals(
            27.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {oneTshirt}, CustomerType.PREMIUM_CUSTOMER)))
        );
        Assertions.assertEquals(
            15.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {oneTshirt}, CustomerType.PLATINUM_CUSTOMER)))
        );
    }

    @Test
    void different_items_have_different_prices() {

        Mockito.when(mockDateTime.getDate()).thenReturn(new Date(0));

        Assertions.assertEquals(
            30.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {oneTshirt}, CustomerType.STANDARD_CUSTOMER)))
        );
        Assertions.assertEquals(
            50.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {oneDress}, CustomerType.STANDARD_CUSTOMER)))
        );
        Assertions.assertEquals(
            100.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {oneJacket}, CustomerType.STANDARD_CUSTOMER)))
        );
    }

    @Test
    void different_dates_get_discounted_prices() throws ParseException {

        Mockito.when(mockDateTime.getDate()).thenReturn(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-14"));

        Assertions.assertEquals(
            30.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {oneTshirt}, CustomerType.STANDARD_CUSTOMER)))
        );
        Assertions.assertEquals(
            40.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {oneDress}, CustomerType.STANDARD_CUSTOMER)))
        );
        Assertions.assertEquals(
            90.0 , Double.parseDouble(controller.getPrice(new Body(new Item[] {oneJacket}, CustomerType.STANDARD_CUSTOMER)))
        );
    }

    @Test
    void customers_have_price_limits_depending_on_their_types(){

        Mockito.when(mockDateTime.getDate()).thenReturn(new Date(0));

        Throwable exception = Assertions.assertThrows(
            Exception.class , () -> controller.getPrice(new Body(new Item[] {tenTshirts}, CustomerType.STANDARD_CUSTOMER))
        );
        Assertions.assertEquals("400 BAD_REQUEST \"Price (300.0) is too high for standard customer\"", exception.getMessage());

        Throwable exception2 = Assertions.assertThrows(
            Exception.class , () -> controller.getPrice(new Body(new Item[] {tenDresses, tenJackets}, CustomerType.PREMIUM_CUSTOMER))
        );
        Assertions.assertEquals("400 BAD_REQUEST \"Price (1350.0) is too high for premium customer\"", exception2.getMessage());

        Throwable exception3 = Assertions.assertThrows(
            Exception.class , () -> controller.getPrice(new Body(new Item[] {tenJackets,tenJackets, tenJackets, tenJackets, tenJackets}, CustomerType.PLATINUM_CUSTOMER))
        );
        Assertions.assertEquals("400 BAD_REQUEST \"Price (2500.0) is too high for platinum customer\"", exception3.getMessage());

    }
}
