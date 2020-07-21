package dev.alnat.moneykeeper.util.hibernate;

import org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Чтение инициализационного файла с заданной кодировкой
 *
 * По умолчания класс MultipleLinesSqlCommandExtractor читает sql файл в кодировке системы,
 * из-за этого происходит инициализация базы с некорректной кодировкой в случае отличия от UTF-8
 *
 * Кроме этого, если бы не MultipleLines то любые многострочные команды не выполнялись бы
 *
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class EncodingMultipleLinesSqlCommandExtractor extends MultipleLinesSqlCommandExtractor {

    private final String SOURCE_CHARSET = "UTF-8";

    @Override
    public String[] extractCommands(Reader reader) {
        String[] extractCommands = super.extractCommands(reader);

        Charset charset = Charset.defaultCharset();
        if (!charset.equals(Charset.forName(SOURCE_CHARSET))) {
            extractCommands = Arrays.stream(extractCommands).map(item -> {
                try {
                    return new String(item.getBytes(), SOURCE_CHARSET).replace("�?", "И");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }).toArray(String[]::new);
        }

        return extractCommands;
    }

}