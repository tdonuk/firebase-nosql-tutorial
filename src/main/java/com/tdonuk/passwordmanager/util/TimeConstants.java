package com.tdonuk.passwordmanager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeConstants {
    public static final long ONE_MINUTE = 1 * 1000 * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final long ONE_DAY = ONE_HOUR * 24;
    public static final long ONE_MONTH = ONE_DAY * 30;

    /**
     * Converts the number of minutes to miliseconds.
     * @param mins number of minutes to convert into miliseconds
     */
    public static long minutes(int mins) {
        return mins * ONE_MINUTE;
    }

    /**
     * Converts the number of months to miliseconds.
     * @param months number of months to convert into miliseconds
     */
    public static long months(int months) {
        return ONE_MONTH * months;
    }

    /**
     * Converts the number of hours to miliseconds.
     * @param hours number of hours to convert into miliseconds
     */
    public static long hours(int hours) {
        return ONE_HOUR * hours;
    }
}