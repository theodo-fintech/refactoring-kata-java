package com.sipios.refactoring.service.impl;

import com.sipios.refactoring.service.DiscountService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * A {@code DiscountServiceImpl} is...
 * TODO: write javadoc...
 */
@Service
public class DiscountServiceImpl implements DiscountService {

    @Override
    public boolean isSeasonal(LocalDate date) {
        // TODO: impl
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
