package com.sipios.refactoring.service;

import com.sipios.refactoring.models.Body;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
