package com.sipios.refactoring.exception;

import com.sipios.refactoring.entity.ConsumerType;

public class HighPriceException extends RuntimeException {

    public HighPriceException(double price, ConsumerType consumerType){
        super ("Price (" + price + ") is too high for "+ consumerType.toString());
    }
}
