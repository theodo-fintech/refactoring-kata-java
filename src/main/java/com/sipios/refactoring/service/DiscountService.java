package com.sipios.refactoring.service;

import java.time.LocalDate;

/**
 * A {@code DiscountService} is...
 * TODO: write javadoc...
 */
public interface DiscountService {

    /**
     * Determines if we are in winter or summer discounts periods
     *
     * @param date
     * @return
     */
    boolean isSeasonal(LocalDate date);

}
