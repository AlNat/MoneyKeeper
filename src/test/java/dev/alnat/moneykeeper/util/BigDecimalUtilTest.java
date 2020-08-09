package dev.alnat.moneykeeper.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static dev.alnat.moneykeeper.util.BigDecimalUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by @author AlNat on 30.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@SuppressWarnings({"BigDecimalMethodWithoutRoundingCalled", "UnpredictableBigDecimalConstructorCall"})
public class BigDecimalUtilTest {

    @Test
    @DisplayName("Проверка сравнения с 0") 
    void zeroTests() {
        assertTrue(isZero(BigDecimal.valueOf(0.0)));
        assertTrue(isZero(BigDecimal.valueOf(0)));
        assertTrue(isZero(BigDecimal.ZERO));
        assertTrue(isZero(new BigDecimal("0")));
        assertTrue(isZero(new BigDecimal("0.0")));

        assertFalse(isZero(BigDecimal.ONE));
        assertFalse(isZero(BigDecimal.valueOf(0.00000000001)));
        assertFalse(isZero(new BigDecimal("0.00000000001")));
    }

    @Test
    @DisplayName("Проверка нахождения минимума из 2 чисел")
    void minTest() {
        assertEquals(new BigDecimal("123.00"), min(new BigDecimal(123), new BigDecimal(234)));
        assertEquals(new BigDecimal("123.00"), min(new BigDecimal(123), new BigDecimal(123)));
        assertEquals(new BigDecimal("123.00"), min(new BigDecimal("123.00"), new BigDecimal("123.00")));
        assertEquals(new BigDecimal("123.00"), min(new BigDecimal("123"), new BigDecimal("123")));

        assertEquals(BigDecimal.ZERO.setScale(2), min(BigDecimal.ZERO, BigDecimal.ONE));

        assertThrows(NullPointerException.class, () -> min(null, new BigDecimal(234)));
        assertThrows(NullPointerException.class, () -> min(new BigDecimal(123), null));
    }

    @Test
    @DisplayName("Проверка нахождения максимума из 2 чисел")
    void maxTest() {
        assertEquals(new BigDecimal("123.00"), max(new BigDecimal(123), new BigDecimal(100)));
        assertEquals(new BigDecimal("123.00"), max(new BigDecimal(123), new BigDecimal(100)));
        assertEquals(new BigDecimal("123.00"), max(new BigDecimal("123.00"), new BigDecimal("123.00")));
        assertEquals(new BigDecimal("123.00"), max(new BigDecimal("123"), new BigDecimal("123")));

        assertEquals(BigDecimal.ONE.setScale(2), max(BigDecimal.ZERO, BigDecimal.ONE));

        assertThrows(NullPointerException.class, () -> max(null, new BigDecimal(234)));
        assertThrows(NullPointerException.class, () -> max(new BigDecimal(123), null));
    }

    @Test
    @DisplayName("Проверка метода положительности числа")
    void testIsPositive() {
        assertTrue(isPositive(new BigDecimal("123.00")));
        assertTrue(isPositive(new BigDecimal("0.01")));
        assertTrue(isPositive(new BigDecimal(123)));
        assertTrue(isPositive(new BigDecimal(0.00000001)));
        assertTrue(isPositive(BigDecimal.ONE));

        assertFalse(isPositive(BigDecimal.ZERO));
        assertFalse(isPositive(new BigDecimal("-123.00")));
        assertFalse(isPositive(new BigDecimal("-0.01")));
        assertFalse(isPositive(new BigDecimal(-123)));
        assertFalse(isPositive(new BigDecimal(-0.000000001)));
        assertFalse(isPositive(BigDecimal.ONE.multiply(new BigDecimal("-1"))));
    }

    @Test
    @DisplayName("Проверка метода неотрицательности числа")
    void testIsNotNegative() {
        assertTrue(isPositiveOrZero(new BigDecimal("123.00")));
        assertTrue(isPositiveOrZero(new BigDecimal("0.01")));
        assertTrue(isPositiveOrZero(new BigDecimal(123)));
        assertTrue(isPositiveOrZero(new BigDecimal(0.00000001)));
        assertTrue(isPositiveOrZero(BigDecimal.ONE));
        assertTrue(isPositiveOrZero(BigDecimal.ZERO));

        assertFalse(isPositiveOrZero(new BigDecimal("-123.00")));
        assertFalse(isPositiveOrZero(new BigDecimal("-0.01")));
        assertFalse(isPositiveOrZero(new BigDecimal(-123)));
        assertFalse(isPositiveOrZero(new BigDecimal(-0.000000001)));
        assertFalse(isPositiveOrZero(BigDecimal.ONE.multiply(new BigDecimal("-1"))));
    }

    @Test
    @DisplayName("Проверка метода отрицательности числа")
    void testIsNegative() {
        assertFalse(isNegative(new BigDecimal("123.00")));
        assertFalse(isNegative(new BigDecimal("0.01")));
        assertFalse(isNegative(new BigDecimal(123)));
        assertFalse(isNegative(new BigDecimal(0.00000001)));
        assertFalse(isNegative(BigDecimal.ONE));
        assertFalse(isNegative(BigDecimal.ZERO));

        assertTrue(isNegative(new BigDecimal("-123.00")));
        assertTrue(isNegative(new BigDecimal("-0.01")));
        assertTrue(isNegative(new BigDecimal(-123)));
        assertTrue(isNegative(new BigDecimal(-0.000000001)));
        assertTrue(isNegative(BigDecimal.ONE.multiply(new BigDecimal("-1"))));
    }

    @Test
    @DisplayName("Проверка метода неположительности числа")
    void testIsNotPositive() {
        assertFalse(isNegativeOrZero(new BigDecimal("123.00")));
        assertFalse(isNegativeOrZero(new BigDecimal("0.01")));
        assertFalse(isNegativeOrZero(new BigDecimal(123)));
        assertFalse(isNegativeOrZero(new BigDecimal(0.00000001)));
        assertFalse(isNegativeOrZero(BigDecimal.ONE));

        assertTrue(isNegativeOrZero(BigDecimal.ZERO));
        assertTrue(isNegativeOrZero(new BigDecimal("-123.00")));
        assertTrue(isNegativeOrZero(new BigDecimal("-0.01")));
        assertTrue(isNegativeOrZero(new BigDecimal(-123)));
        assertTrue(isNegativeOrZero(new BigDecimal(-0.000000001)));
        assertTrue(isNegativeOrZero(BigDecimal.ONE.multiply(new BigDecimal("-1"))));
    }

}
