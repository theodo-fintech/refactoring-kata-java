package com.sipios.refactoring.services;


import com.sipios.refactoring.exceptions.FunctionalException;
import com.sipios.refactoring.utils.Constantes;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class DiscountService {


    private Map<String, Double> summerOrWinterDiscount = Map.of(Constantes.ITEM_DRESS, 0.8, Constantes.ITEM_JACKET, 0.9, Constantes.ITEM_TSHIRT, 1d);

    public static final Predicate<LocalDate> summerOrWinterPeriodPredicate = date -> (date.getDayOfMonth() < 15 && date.getDayOfMonth() > 5 && date.getMonthValue() == 6) ||
        (date.getDayOfMonth() < 15 && date.getDayOfMonth() > 5 && date.getMonthValue() == 1);


    /**
     * fetch Discount value by customer type
     * @param customerType the given customer type
     * @return the discount factor
     */
    public Double getCustomerDiscountByCustomerType(String customerType) {
        switch (customerType) {
            case Constantes.STANDARD_CUSTOMER:
                return 1d;
            case Constantes.PREMIUM_CUSTOMER:
                return 0.9d;
            case Constantes.PLATINUM_CUSTOMER:
                return 0.5d;
            default:
                throw new FunctionalException("customer type not supported");
        }
    }

    /**
     * fetch Winter or summer discount referential
     *
     * @return the map (as cache) containing the mapping between Item and discount value
     */
    public Map<String, Double> fetchWinterOrSummerDiscountReferential() {
        return summerOrWinterDiscount;
    }
}



