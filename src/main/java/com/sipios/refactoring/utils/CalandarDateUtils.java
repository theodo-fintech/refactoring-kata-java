package com.sipios.refactoring.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class CalandarDateUtils {
    public static final TimeZone PARIS_TIMEZONE = TimeZone.getTimeZone("Europe/Paris");

    public static boolean isWinter() {
        Calendar calendar = Calendar.getInstance(PARIS_TIMEZONE);
        calendar.setTime(new Date());

        return !(CalandarDateUtils.isBetween5And15OfTheMonth(calendar) && calendar.get(Calendar.MONTH) == Calendar.JUNE)
            && !(CalandarDateUtils.isBetween5And15OfTheMonth(calendar) && calendar.get(Calendar.MONTH) == Calendar.JANUARY);
    }

    public static boolean isBetween5And15OfTheMonth(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) < 15 && calendar.get(Calendar.DAY_OF_MONTH) > 5;
    }
}
