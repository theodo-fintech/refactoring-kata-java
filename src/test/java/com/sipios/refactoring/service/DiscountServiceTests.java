package com.sipios.refactoring.service;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A {@code DiscountServiceTests} is...
 * TODO: write javadoc...
 */
public class DiscountServiceTests extends UnitTest {

    public DiscountService discountService;

    @Test
    public void isSeasonal_whenDateFallsInSummerDiscountPeriod_shouldReturnTrue() {
        Stream.of("2024-06-06", "2024-06-07", "2024-06-13", "2024-06-14")
            .map(LocalDate::parse)
            .forEach(date -> assertTrue(discountService.isSeasonal(date)));
    }

    @Test
    public void isSeasonal_whenDateNotInSummerOrWinterDiscountPeriod_shouldReturnFalse() {
        Stream.of("2024-05-24", "2024-04-15", "2024-07-5", "2024-08-8")
            .map(LocalDate::parse)
            .forEach(date -> assertFalse(discountService.isSeasonal(date)));
    }

}
