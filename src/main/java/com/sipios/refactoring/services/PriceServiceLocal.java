package com.sipios.refactoring.services;

import com.sipios.refactoring.models.dtos.CartItem;
import com.sipios.refactoring.models.dtos.CustomerType;
import com.sipios.refactoring.models.dtos.ShoppingCartRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PriceServiceLocal implements PriceService {

    private static boolean isSeasonalDiscountPeriod(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        return !(
            cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                cal.get(Calendar.MONTH) == Calendar.JUNE
        ) &&
            !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                    cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                    cal.get(Calendar.MONTH) == Calendar.JANUARY
            ) ;
    }

    private static final Map<CustomerType,Double> customerPlanDiscounts = Stream.of(new Object[][] {
        { CustomerType.STANDARD_CUSTOMER, 1d },
        { CustomerType.PREMIUM_CUSTOMER, 0.9d },
        { CustomerType.PLATINUM_CUSTOMER, 0.5d }
    }).collect(Collectors.toMap(data -> (CustomerType) data[0], data -> (Double) data[1]));

    private static final Map<String,Double> lowSeasonDiscounts = Stream.of(new Object[][] {
        { "TSHIRT", 1d },
        { "DRESS", 0.9d },
        { "JACKET", 0.5d }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));


    private static final Map<String,Double> itemPriceList = Stream.of(new Object[][] {
        { "TSHIRT", 30d },
        { "DRESS", 50d },
        { "JACKET", 100d }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

    public double getCustomerDiscount(CustomerType customerType) {
        if ( !customerPlanDiscounts.containsKey(customerType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            return customerPlanDiscounts.get(customerType).doubleValue();
        }
    }

    private double computeDiscount(String type){
        if ( !customerPlanDiscounts.containsKey(type)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            return customerPlanDiscounts.get(type).doubleValue();
        }
    }
    @Override
    public double getTotalPrice(ShoppingCartRequest shoppingCartRequest) {
        double discount = computeDiscount(shoppingCartRequest.getType());
        Map<String,Double> currentDiscount = isSeasonalDiscountPeriod()? Collections.emptyMap():lowSeasonDiscounts;

        return Arrays.stream(shoppingCartRequest.getItems())
            .mapToDouble(item ->
                itemPriceList.getOrDefault(item.getType(), 0d)
                    * item.getNb() * currentDiscount.getOrDefault(item.getType(),1d)
            )
            .map(price -> price * discount)
            .sum();
    }
}
