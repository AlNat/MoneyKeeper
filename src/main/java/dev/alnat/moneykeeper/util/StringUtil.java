package dev.alnat.moneykeeper.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Набор вспомогательных методов по работе со строками
 *
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class StringUtil {

    /**
     * Метод проверки строки на пустоту или null
     *
     * @param string строка для проверки
     * @return true если она пуста или равна null, иначе false
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Метод получения всего стек-трейса ошибки в виде строки
     *
     * @param e ошибка
     * @return весь ее стек-трейс в одной строке (но с переводами строк внутри)
     */
    public static String getStackTrace(Exception e) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
