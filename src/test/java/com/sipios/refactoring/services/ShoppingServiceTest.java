package com.sipios.refactoring.services;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.dtos.PurchaseItem;
import com.sipios.refactoring.utils.Constantes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Map;

class ShoppingServiceTest extends UnitTest {


    @Mock
    DiscountService discountService;

    @Mock
    PriceService priceService;

    @InjectMocks
    ShoppingService shoppingService;

    @Test
    void getPriceByDate_except_winter_discount_applied() {

        double givenDressPrice = 30d;
        double givenWinterDiscount = .5d;
        double givenCustomerTypeDiscount = .9d;

        // mocking
        Mockito.when(priceService.fetchPriceByItemType(Constantes.ITEM_DRESS)).thenReturn(givenDressPrice);
        Mockito.when(discountService.getCustomerDiscountByCustomerType(Constantes.PREMIUM_CUSTOMER)).thenReturn(givenCustomerTypeDiscount);
        Mockito.when(discountService.fetchWinterOrSummerDiscountReferential()).thenReturn(Map.of(Constantes.ITEM_DRESS, givenWinterDiscount));

        var givenDate = LocalDate.of(2024, 01, 10);
        Assertions.assertEquals(3 * givenDressPrice * givenCustomerTypeDiscount * givenWinterDiscount, shoppingService.getPriceByDate(givenDate, Constantes.PREMIUM_CUSTOMER, new PurchaseItem[]{
            new PurchaseItem(Constantes.ITEM_DRESS, 3)
        }));

    }


    @Test
    void getPriceByDate_except_summer_discount_applied() {

        double givenDressPrice = 30d;
        double givenSummerDiscount = .5d;
        double givenCustomerTypeDiscount = .9d;

        // mocking
        Mockito.when(priceService.fetchPriceByItemType(Constantes.ITEM_DRESS)).thenReturn(givenDressPrice);
        Mockito.when(discountService.getCustomerDiscountByCustomerType(Constantes.PREMIUM_CUSTOMER)).thenReturn(givenCustomerTypeDiscount);
        Mockito.when(discountService.fetchWinterOrSummerDiscountReferential()).thenReturn(Map.of(Constantes.ITEM_DRESS, givenSummerDiscount));

        var givenDate = LocalDate.of(2024, 06, 10);
        Assertions.assertEquals(3 * givenDressPrice * givenCustomerTypeDiscount * givenSummerDiscount, shoppingService.getPriceByDate(givenDate, Constantes.PREMIUM_CUSTOMER, new PurchaseItem[]{
            new PurchaseItem(Constantes.ITEM_DRESS, 3)
        }));

    }



    @Test
    void getPriceByDate_except_winterORsummer_discount_not_applied() {

        double givenDressPrice = 30d;
        double givenWinterDiscount = .5d;
        double givenCustomerTypeDiscount = .9d;

        // mocking
        Mockito.when(priceService.fetchPriceByItemType(Constantes.ITEM_DRESS)).thenReturn(givenDressPrice);
        Mockito.when(discountService.getCustomerDiscountByCustomerType(Constantes.PREMIUM_CUSTOMER)).thenReturn(givenCustomerTypeDiscount);
        Mockito.when(discountService.fetchWinterOrSummerDiscountReferential()).thenReturn(Map.of(Constantes.ITEM_DRESS, givenWinterDiscount));

        var givenDate = LocalDate.of(2024, 02, 10);
        Assertions.assertEquals(3 * givenDressPrice * givenCustomerTypeDiscount , shoppingService.getPriceByDate(givenDate, Constantes.PREMIUM_CUSTOMER, new PurchaseItem[]{
            new PurchaseItem(Constantes.ITEM_DRESS, 3)
        }));

    }


}
