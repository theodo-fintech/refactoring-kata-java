package com.sipios.refactoring.entity;

public enum ConsumerType {
    STANDARD_CUSTOMER ("standard customer"),
    PREMIUM_CUSTOMER ("premium customer"),
    PLATINUM_CUSTOMER ("platinium customer");

    private final String value;

    ConsumerType (String value ) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
