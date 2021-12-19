package com.sipios.refactoring.service;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sipios.refactoring.dto.ItemDto;

@ExtendWith(MockitoExtension.class)
public class ShoppingServiceTests {

	@InjectMocks
    private ShoppingService service;

	@Test
	void getDiscountForCustomerTypeStandardTest() {
		Assertions.assertEquals(1.0, service.getDiscountForCustomerType(ShoppingService.STANDARD_CUSTOMER));
	}
	
	@Test
	void getDiscountForCustomerTypePremiumTest() {
		Assertions.assertEquals(0.9, service.getDiscountForCustomerType(ShoppingService.PREMIUM_CUSTOMER));
	}
	
	@Test
	void getDiscountForCustomerTypePlatinumTest() {
		Assertions.assertEquals(0.5, service.getDiscountForCustomerType(ShoppingService.PLATINUM_CUSTOMER));
	}
	
	@Test
	void isWinterDiscountPeriodTest() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.set(2021, 11, 11);

        Assertions.assertFalse(service.isDiscountPeriod(cal));
	}
	
	@Test
	void isWinterDiscountPeriodTrueTest() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.set(2021, 0, 11);
        Assertions.assertTrue(service.isDiscountPeriod(cal));
	}
	
	@Test
	void isSummerDiscountPeriodTest() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.set(2021, 11, 11);
        Assertions.assertFalse(service.isDiscountPeriod(cal));
	}
	
	@Test
	void isSummerDiscountPeriodTrueTest() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.set(2021, 5, 11);
        Assertions.assertTrue(service.isDiscountPeriod(cal));
	}

	@Test
	void getPriceForItemsAndCustomerTest() {
        ItemDto[] tshirts = {new ItemDto(ShoppingService.TSHIRT,2)};
        ItemDto[] dresses = {new ItemDto(ShoppingService.DRESS,2)};
        ItemDto[] jackets = {new ItemDto(ShoppingService.JACKET,2)};
        
        //THISRTs
        Assertions.assertEquals(60, service.getPriceForItemsAndCustomer(tshirts, ShoppingService.STANDARD_CUSTOMER_DISCOUNT, false));
        Assertions.assertEquals(60, service.getPriceForItemsAndCustomer(tshirts, ShoppingService.STANDARD_CUSTOMER_DISCOUNT, true));
        
        Assertions.assertEquals(54, service.getPriceForItemsAndCustomer(tshirts, ShoppingService.PREMIUM_CUSTOMER_DISCOUNT, false));
        Assertions.assertEquals(54, service.getPriceForItemsAndCustomer(tshirts, ShoppingService.PREMIUM_CUSTOMER_DISCOUNT, true));
        
        Assertions.assertEquals(30, service.getPriceForItemsAndCustomer(tshirts, ShoppingService.PLATINUM_CUSTOMER_DISCOUNT, false));
        Assertions.assertEquals(30, service.getPriceForItemsAndCustomer(tshirts, ShoppingService.PLATINUM_CUSTOMER_DISCOUNT, true));
        
        //DRESSEs
        Assertions.assertEquals(100, service.getPriceForItemsAndCustomer(dresses, ShoppingService.STANDARD_CUSTOMER_DISCOUNT, false));
        Assertions.assertEquals(80, service.getPriceForItemsAndCustomer(dresses, ShoppingService.STANDARD_CUSTOMER_DISCOUNT, true));
        
        Assertions.assertEquals(90, service.getPriceForItemsAndCustomer(dresses, ShoppingService.PREMIUM_CUSTOMER_DISCOUNT, false));
        Assertions.assertEquals(72, service.getPriceForItemsAndCustomer(dresses, ShoppingService.PREMIUM_CUSTOMER_DISCOUNT, true));
        
        Assertions.assertEquals(50, service.getPriceForItemsAndCustomer(dresses, ShoppingService.PLATINUM_CUSTOMER_DISCOUNT, false));
        Assertions.assertEquals(40, service.getPriceForItemsAndCustomer(dresses, ShoppingService.PLATINUM_CUSTOMER_DISCOUNT, true));
        
        //JACKETs
        Assertions.assertEquals(200, service.getPriceForItemsAndCustomer(jackets, ShoppingService.STANDARD_CUSTOMER_DISCOUNT, false));
        Assertions.assertEquals(180, service.getPriceForItemsAndCustomer(jackets, ShoppingService.STANDARD_CUSTOMER_DISCOUNT, true));
        
        Assertions.assertEquals(180, service.getPriceForItemsAndCustomer(jackets, ShoppingService.PREMIUM_CUSTOMER_DISCOUNT, false));
        Assertions.assertEquals(162, service.getPriceForItemsAndCustomer(jackets, ShoppingService.PREMIUM_CUSTOMER_DISCOUNT, true));
        
        Assertions.assertEquals(100, service.getPriceForItemsAndCustomer(jackets, ShoppingService.PLATINUM_CUSTOMER_DISCOUNT, false));
        Assertions.assertEquals(90, service.getPriceForItemsAndCustomer(jackets, ShoppingService.PLATINUM_CUSTOMER_DISCOUNT, true));
	}
		
}
