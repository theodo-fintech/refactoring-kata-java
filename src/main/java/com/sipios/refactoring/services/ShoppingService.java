package com.sipios.refactoring.services;

import com.sipios.refactoring.dtos.PurchaseItem;
import com.sipios.refactoring.exceptions.FunctionalException;
import com.sipios.refactoring.utils.Constantes;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.ToDoubleFunction;


@Service
public class ShoppingService {


    private final PriceService priceService;

    private final DiscountService discountService;


    BiFunction<String, LocalDate, ToDoubleFunction<PurchaseItem>> applyDiscountByCustomerTypeAndPeriod;


    public ShoppingService(PriceService priceService, DiscountService discountService) {
        this.priceService = priceService;
        this.discountService = discountService;

        // fetch summerOrWinter Discount Map


        applyDiscountByCustomerTypeAndPeriod = (customerType, date) -> {
            var discountForCustomerType = discountService.getCustomerDiscountByCustomerType(customerType);
            var summerOrWinterDiscount = discountService.fetchWinterOrSummerDiscountReferential();

            return discountService.summerOrWinterPeriodPredicate.test(date) ? purchaseItem ->
                    priceService.fetchPriceByItemType(purchaseItem.getType()) * discountForCustomerType * purchaseItem.getNb() * summerOrWinterDiscount.get(purchaseItem.getType())
                    :
                    purchaseItem ->
                            priceService.fetchPriceByItemType(purchaseItem.getType()) * discountForCustomerType * purchaseItem.getNb();
        };


    }


    /**
     * fetch the global price of a purchase depends on the give date
     *
     * @param date          the date of purchase
     * @param customerType  the given customer type
     * @param purchaseItems the items purchased
     * @return the prices
     */
    public Double getPriceByDate(LocalDate date, String customerType, PurchaseItem[] purchaseItems) throws FunctionalException {
        if (purchaseItems.length == 0) {
            return 0d;
        }

        double p = Arrays.asList(purchaseItems).stream()
                .mapToDouble(applyDiscountByCustomerTypeAndPeriod.apply(customerType, date))
                .sum();
        // check price threshold
        switch (customerType) {
            case Constantes.PREMIUM_CUSTOMER:
                if (p > 800) {
                    throw new FunctionalException(String.format("Price ( %d ) is too high for premium customer", p));
                }
                return p;
            case Constantes.PLATINUM_CUSTOMER:
                if (p > 2000) {
                    throw new FunctionalException(String.format("Price ( %d ) is too high for platinum customer", p));
                }
                return p;
            default:
                if (p > 200) {
                    throw new FunctionalException(String.format("Price (  %d ) is too high for standard customer", p));
                }
                return p;
        }
    }


}
