package dev.alnat.moneykeeper.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by @author AlNat on 30.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class StringUtilTest {

    @Test
    @DisplayName("Тестирования метода проверки строки на отсутствие")
    void testIsNullOrEmpty() {
        assertTrue(StringUtil.isNullOrEmpty(null));
        assertTrue(StringUtil.isNullOrEmpty(""));
        assertTrue(StringUtil.isNullOrEmpty(new String(new byte[0])));

        assertFalse(StringUtil.isNullOrEmpty(" "));
        assertFalse(StringUtil.isNullOrEmpty("123"));
        assertFalse(StringUtil.isNullOrEmpty(String.valueOf('\n')));
        assertFalse(StringUtil.isNullOrEmpty(String.valueOf('\t')));
        assertFalse(StringUtil.isNullOrEmpty(String.valueOf(123)));
        assertFalse(StringUtil.isNullOrEmpty(new String(new byte[1])));
    }


    @Test
    @DisplayName("Тестирования метода проверки строки на отсутствие")
    void testGenerateKey() {
        assertEquals("key", StringUtil.generateKey("key"));
        assertEquals("key_123", StringUtil.generateKey("key 123"));
        assertEquals("key_key", StringUtil.generateKey("key key"));

        assertEquals("abc", StringUtil.generateKey("абц"));
        assertEquals("abc_123", StringUtil.generateKey("абц 123"));
        assertEquals("abc_abc", StringUtil.generateKey("абц абц"));

        assertEquals("super_bolshoe_imya_vot_takoe", StringUtil.generateKey("Супер большое имя вот такое"));
        assertEquals("super_bolshoe_imya", StringUtil.generateKey("СУПЕР БОЛЬШОЕ ИМЯ"));
    }


    @Test
    @DisplayName("Тестирования метода транслитерации по ГОСТ")
    void testTransliterate() {
        assertEquals("klyuch'", StringUtil.transliterate("ключь"));
        assertEquals("dom", StringUtil.transliterate("дом"));

        String l = StringUtil
                .transliterate("Все люди рождаются свободными и равными в своём достоинстве и правах. Они наделены разумом и совестью и должны поступать в отношении друг друга в духе братства.")
                .replaceAll("'", "");
        assertEquals("Vse lyudi rozhdayutsya svobodnymi i ravnymi v svoyom dostoinstve i pravah. Oni nadeleny razumom i sovestyu i dolzhny postupat v otnoshenii drug druga v duhe bratstva.",
                l); // Для удобства немного отойдем от стандарта ГОСТ и удалим все апострофы - они тут некорректны
    }

}
