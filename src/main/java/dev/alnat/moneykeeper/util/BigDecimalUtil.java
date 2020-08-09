package dev.alnat.moneykeeper.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by @author AlNat on 30.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class BigDecimalUtil {


    /**
     * Нахождение минимального из 2 элементов с точностью 2 знака
     *
     * @param first первый элемент
     * @param second второй
     * @return минимального из 2 элементов с точностью 2 знака
     */
    public static BigDecimal min(BigDecimal first, BigDecimal second) {
        return min(first, second, 2);
    }

    /**
     * Нахождение минимального из 2 элементов с указанной точностью
     *
     * @param first первый элемент
     * @param second второй
     * @param scale точность
     * @return минимального из 2 элементов с точностью scale
     */
    public static BigDecimal min(BigDecimal first, BigDecimal second, int scale) {
        if (first == null) throw new NullPointerException("Первое число не должна быть пустое");
        if (second == null) throw new NullPointerException("Второе число не должна быть пустое");

        return first.setScale(scale, RoundingMode.HALF_EVEN).min(second.setScale(scale, RoundingMode.HALF_EVEN));
    }

    /**
     * Нахождение максимального из 2 элементов с точностью 2 знака
     *
     * @param first первый элемент
     * @param second второй
     * @return максимального из 2 элементов с точностью 2 знака
     */
    public static BigDecimal max(BigDecimal first, BigDecimal second) {
        return max(first, second, 2);
    }

    /**
     * Нахождение максимального из 2 элементов с указанной точностью
     *
     * @param first первый элемент
     * @param second второй
     * @param scale точность
     * @return максимального из 2 элементов с точностью scale
     */
    public static BigDecimal max(BigDecimal first, BigDecimal second, int scale) {
        if (first == null) throw new NullPointerException("Первое число не должна быть пустое");
        if (second == null) throw new NullPointerException("Второе число не должна быть пустое");

        return first.setScale(scale, RoundingMode.HALF_EVEN).max(second.setScale(scale, RoundingMode.HALF_EVEN));
    }


    public static boolean isZero(BigDecimal first) {
        if (first == null) throw new NullPointerException("Число не должна быть пустое");

        return first.compareTo(BigDecimal.ZERO) == 0;
    }

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
