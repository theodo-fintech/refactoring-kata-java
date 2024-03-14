package com.sipios.refactoring.pricing;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

public enum Discounts {
    DRESS_WINTER(
        LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 5),
        LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 15),
        "DRESS",
        0.8
    ),
    DRESS_SUMMER(
        LocalDate.of(LocalDate.now().getYear(), Month.JUNE, 5),
        LocalDate.of(LocalDate.now().getYear(), Month.JUNE, 15),
        "DRESS",
        0.8
    ),
    JACKET_WINTER(
        LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 5),
        LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 15),
        "JACKET",
        0.9
    ),
    JACKET_SUMMER(
        LocalDate.of(LocalDate.now().getYear(), Month.JUNE, 5),
        LocalDate.of(LocalDate.now().getYear(), Month.JUNE, 15),
        "JACKET",
        0.9
    );

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String itemType;
    private final double discount;

    Discounts(LocalDate startDate, LocalDate endDate, String ItemType, double discount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.itemType = ItemType;
        this.discount = discount;
    }

    public static Optional<Discounts> findItemTypeDiscount(String itemType, Clock clock) {
        return Arrays.stream(values())
            .filter(discount -> discount.itemType.equals(itemType))
            .filter(discount -> LocalDate.now(clock).isAfter(discount.startDate) && LocalDate.now(clock).isBefore(discount.endDate))
            .findFirst();
    }

    public double getDiscount() {
        return discount;
    }
}
