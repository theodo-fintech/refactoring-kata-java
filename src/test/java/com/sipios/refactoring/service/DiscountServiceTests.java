package com.sipios.refactoring.service;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DiscountServiceTests extends UnitTest {

    @Autowired
    public DiscountService discountService;

    @Test
    public void isSeasonal_whenDateFallsInSummerDiscountPeriod_shouldReturnTrue() {
        Stream.of("2024-06-06", "2024-06-07", "2024-06-13", "2024-06-14")
            .map(LocalDate::parse)
            .forEach(date -> assertTrue(discountService.isSeasonal(date)));
    }

    @Test
    public void isSeasonal_whenDateNotInSummerOrWinterDiscountPeriod_shouldReturnFalse() {
        Stream.of("2024-05-24", "2024-04-15", "2024-07-05", "2024-08-08")
            .map(LocalDate::parse)
            .forEach(date -> assertFalse(discountService.isSeasonal(date)));
    }

}
