package com.sipios.refactoring.service.impl;

import com.sipios.refactoring.service.DiscountService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

/**
 * A {@code DiscountServiceImpl} is...
 * TODO: write javadoc...
 */
@Service
public class DiscountServiceImpl implements DiscountService {

    @Override
    public boolean isSeasonal(LocalDate date) {
        var month = date.getMonth();
        var dayOfMonth = date.getDayOfMonth();
        var fallsWithinRange = dayOfMonth > 5 && dayOfMonth < 15;
        return (fallsWithinRange && (month == Month.JANUARY || month == Month.JUNE));
    }

}
