package dev.alnat.moneykeeper.util;

import java.math.BigDecimal;

/**
 * Created by @author AlNat on 30.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class BigDecimalUtil {

    public static boolean isPositive(BigDecimal in) {
        return in.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isPositiveOrZero(BigDecimal in) {
        return in.compareTo(BigDecimal.ZERO) >= 0;
    }


    public static boolean isNegative(BigDecimal in) {
        return in.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean isNegativeOrZero(BigDecimal in) {
        return in.compareTo(BigDecimal.ZERO) <= 0;
    }

}
