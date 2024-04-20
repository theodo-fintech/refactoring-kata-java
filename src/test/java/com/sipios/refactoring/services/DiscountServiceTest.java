package com.sipios.refactoring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

class DiscountServiceTest {

    DiscountService discountService = new DiscountService();


    @Test
    void fetchWinterOrSummerDiscountReferential() {
        var summerOrWinterDiscount = Map.of("givenItem", 0.5d);
        ReflectionTestUtils.setField(discountService, "summerOrWinterDiscount", summerOrWinterDiscount);

        Assertions.assertIterableEquals(discountService.fetchWinterOrSummerDiscountReferential()
            .entrySet(), summerOrWinterDiscount.entrySet());


    }
}
