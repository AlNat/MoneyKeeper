package dev.alnat.moneykeeper.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * Блок алфавита для транслитерации
     */
    private static final Map<Character, String> alphabet;
    static  {
        alphabet = new HashMap<Character, String>();
        alphabet.put('а', "a");
        alphabet.put('б', "b");
        alphabet.put('в', "v");
        alphabet.put('г', "g");
        alphabet.put('д', "d");
        alphabet.put('е', "e");
        alphabet.put('ё', "yo");
        alphabet.put('ж', "zh");
        alphabet.put('з', "z");
        alphabet.put('и', "i");
        alphabet.put('й', "j");
        alphabet.put('к', "k");
        alphabet.put('л', "l");
        alphabet.put('м', "m");
        alphabet.put('н', "n");
        alphabet.put('о', "o");
        alphabet.put('п', "p");
        alphabet.put('р', "r");
        alphabet.put('с', "s");
        alphabet.put('т', "t");
        alphabet.put('у', "u");
        alphabet.put('ф', "f");
        alphabet.put('х', "h");
        alphabet.put('ц', "cz");
        alphabet.put('ч', "ch");
        alphabet.put('ш', "sh");
        alphabet.put('щ', "shh");
        alphabet.put('ъ', "''");
        alphabet.put('ы', "y'");
        alphabet.put('ь', "'");
        alphabet.put('э', "e'");
        alphabet.put('ю', "yu");
        alphabet.put('я', "ya");
    }

    /**
     * Метод транслитерации из кириллических символов в латиницу
     * Реализует ГОСТ 7.79-2000
     *
     * Можно было бы воспользоваться IBM ICU, но тащить такую библиотеку только для перевода - мне кается лишнее
     *
     * @param input строка для транслитерации
     * @return результирующая строка
     */
    public static String transliterate(String input) {
        StringBuilder dst = new StringBuilder();
        char c;
        boolean upperCase;

        for (int i = 0; i < input.length(); i++){
            c = Character.toLowerCase(input.charAt(i));
            upperCase = Character.isUpperCase(input.charAt(i));

            if (alphabet.containsKey(c)) {
                // Если после {ц} стоят буквы {и, й, е, ы}, то пишется {c}
                // В остальных случаях пишется {cz}
                if (c == 'ц') {
                    // Если не последний символ
                    if (i != input.length() - 1) {
                        char next = Character.toLowerCase(input.charAt(i + 1));
                        if ((i < input.length() - 1) &&
                                (next == 'и' || next == 'е' || next == 'й' || next == 'ы' || !Character.isAlphabetic(next))) {
                            dst.append(upperCase ? "C" : "c");
                            continue;
                        }
                    } else {
                        dst.append(upperCase ? "C" : "c");
                        continue;
                    }
                }

                dst.append(upperCase ? alphabet.get(c).toUpperCase() : alphabet.get(c));
            } else {
                dst.append(input.charAt(i));
            }
        }

        return dst.toString();
    }

    /**
     * Метод генерации технического идентификатора по имени
     *
     * Меняет всю кириллицу на латиницу, заменяет пробелы на подчеркивание и все приводит к нижнему регистру
     *
     * @param name имя, из которого нужно сгенерировать идентификатор
     * @return сформированный идентификатор
     */
    public static String generateKey(String name) {
        return transliterate(name.toLowerCase().replaceAll(" ", "_"))
                .replaceAll("'", "");
    }

}
