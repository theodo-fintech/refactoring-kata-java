package com.sipios.refactoring.services;

import com.sipios.refactoring.models.CalculatePriceRequest;
import com.sipios.refactoring.models.CalculatePriceRequestType;
import com.sipios.refactoring.models.Item;
import com.sipios.refactoring.utils.CalandarDateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;

import static com.sipios.refactoring.models.CalculatePriceRequestType.*;

@Service
public class PriceService {

    public double computeDiscountByType(CalculatePriceRequestType calculatePriceRequest) {
        switch (calculatePriceRequest) {
            case STANDARD_CUSTOMER:
                return 1;
            case PREMIUM_CUSTOMER:
                return 0.9;
            case PLATINUM_CUSTOMER:
                return 0.5;
            default:
                return 0;
        }
    }

    public double calculatePrice(CalculatePriceRequest calculatePriceRequest, double discount) {
        ToDoubleFunction<Item> itemToDoubleFunctionBySeason = CalandarDateUtils.isWinter() ?
            (item -> calculatePriceForWinter(discount, item)) : (item -> calculatePriceForSummer(discount, item));

        return Arrays.stream(calculatePriceRequest.getItems())
            .mapToDouble(itemToDoubleFunctionBySeason)
            .sum();
    }

    private double calculatePriceForWinter(double discount, Item item) {
        switch (item.getType()) {
            case TSHIRT:
                return 30 * item.getNb() * discount;
            case DRESS:
                return 50 * item.getNb() * discount;
            case JACKET:
                return 100 * item.getNb() * discount;
            default:
                return 0;
        }
    }

    private double calculatePriceForSummer(double discount, Item item) {
        switch (item.getType()) {
            case TSHIRT:
                return 30 * item.getNb() * discount;
            case DRESS:
                return 50 * item.getNb() * 0.8 * discount;
            case JACKET:
                return 100 * item.getNb() * 0.9 * discount;
            default:
                return 0;
        }
    }

    public void priceAndRequestTypeCheck(CalculatePriceRequestType calculatePriceRequestType, double price) {
        if (calculatePriceRequestType == STANDARD_CUSTOMER && price > 200) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, buildExceptionMessage(price, calculatePriceRequestType));
        }

        if (calculatePriceRequestType == PREMIUM_CUSTOMER && price > 800) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, buildExceptionMessage(price, calculatePriceRequestType));
        }

        if (calculatePriceRequestType == PLATINUM_CUSTOMER && price > 2000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, buildExceptionMessage(price, calculatePriceRequestType));
        }
    }

    private String buildExceptionMessage(double price, CalculatePriceRequestType calculatePriceRequestType) {
        return "Price (" + price + ") is too high for " + calculatePriceRequestType.name() + " customer";
    }
}
