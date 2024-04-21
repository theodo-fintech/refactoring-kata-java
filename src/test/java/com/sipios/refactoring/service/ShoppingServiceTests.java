package com.sipios.refactoring.service;

import com.sipios.refactoring.models.Body;
import com.sipios.refactoring.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingServiceTests {

    private ShoppingService shoppingService;
    private Calendar nonDiscountCalendar;
    private Calendar discountCalendar;

    @BeforeEach
    void setUp() {
        shoppingService = new ShoppingService();
        nonDiscountCalendar = getCustomCalendar(2021, Calendar.JUNE, 20); // Non-discount period
        discountCalendar = getCustomCalendar(2021, Calendar.JANUARY, 10); // Discount period
    }

    private Calendar getCustomCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        calendar.set(year, month, day, 12, 0); // set time to midday
        return calendar;
    }

    @Test
    public void testGetPrice_StandardCustomer_NoItems() {
        Body body = new Body(null, "STANDARD_CUSTOMER");
        String price = shoppingService.getPrice(body, nonDiscountCalendar);
        assertEquals("0", price);
    }

    @Test
    public void testGetPrice_StandardCustomer_TShirtNonDiscountPeriod() {
        Item[] items = {new Item("TSHIRT", 3)};
        Body body = new Body(items, "STANDARD_CUSTOMER");
        String price = shoppingService.getPrice(body, nonDiscountCalendar);
        assertEquals("90.0", price);
    }

    @Test
    public void testGetPrice_PremiumCustomer_DressDiscountPeriod() {
        Item[] items = {new Item("DRESS", 2)};
        Body body = new Body(items, "PREMIUM_CUSTOMER");
        String price = shoppingService.getPrice(body, discountCalendar);
        assertEquals("72.0", price); // 2 * 50 * 0.8 (Discount price for DRESS) * 0.9 (Discount for PREMIUM_CUSTOMER)
    }

    @Test
    public void testGetPrice_PlatinumCustomer_JacketDiscountPeriod() {
        Item[] items = {new Item("JACKET", 1)};
        Body body = new Body(items, "PLATINUM_CUSTOMER");
        String price = shoppingService.getPrice(body, discountCalendar);
        assertEquals("45.0", price); // 1 * 100 * 0.9 (Discount price for JACKET) * 0.5 (Discount for PLATINUM_CUSTOMER)
    }

    @Test
    public void testGetPrice_ThrowsException_StandardCustomerHighPrice() {
        Item[] items = {new Item("JACKET", 8)};  // 8 * 100 * 1.0 = 800, above standard customer limit
        Body body = new Body(items, "STANDARD_CUSTOMER");
        Exception exception = assertThrows(ResponseStatusException.class, () -> shoppingService.getPrice(body, nonDiscountCalendar));
        assertTrue(exception.getMessage().contains("Price (800.0) is too high for standard customer"));
        assertEquals(HttpStatus.BAD_REQUEST, ((ResponseStatusException)exception).getStatus());
    }

    @Test
    public void testGetPrice_ThrowsException_PremiumCustomerHighPrice() {
        Item[] items = {new Item("JACKET", 9)};  // 9 * 100 * 0.9 = 810, above premium customer limit
        Body body = new Body(items, "PREMIUM_CUSTOMER");
        Exception exception = assertThrows(ResponseStatusException.class, () -> shoppingService.getPrice(body, nonDiscountCalendar));
        assertTrue(exception.getMessage().contains("Price (810.0) is too high for premium customer"));
        assertEquals(HttpStatus.BAD_REQUEST, ((ResponseStatusException)exception).getStatus());
    }
}
