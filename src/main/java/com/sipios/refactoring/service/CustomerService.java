package com.sipios.refactoring.service;

import com.sipios.refactoring.model.CustomerType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerService {
   public static double computeCustomerDiscount(CustomerType customerType) {
        final double customerDiscount;
        switch (customerType) {
            case STANDARD_CUSTOMER:
                customerDiscount = 1;
                break;
            case PREMIUM_CUSTOMER:
                customerDiscount = 0.9;
                break;
            case PLATINUM_CUSTOMER:
                customerDiscount = 0.5;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return customerDiscount;
    }
}
