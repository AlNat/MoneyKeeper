package dev.alnat.moneykeeper.model.enums;

/**
 * Операция, доступная пользователю
 *
 * Содержит всевозможные авторизации
 * Далее превращается в набор GrantedAuthority, которыми обладают пользователи
 *
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
public enum UserOperation {

    // Операции работы с дашбордами
    DASHBOARD("Просмотр дашбордов"),

    // Операции взаимодействия со транзакциями
    TRANSACTION_LIST("Просмотр списка"),
    TRANSACTION("Просмотр"),
    TRANSACTION_DELETE("Удаление"),
    TRANSACTION_CHANGE("Изменение"),
    TRANSACTION_CREATE("Создание"),

    // Операции взаимодействия с категориями
    CATEGORY("Просмотр списка"),
    CATEGORY_DELETE("Удаление"),
    CATEGORY_CHANGE("Изменение"),
    CATEGORY_CREATE("Создание"),

    // Операции взаимодействия со счетами
    ACCOUNT_LIST("Просмотр списка"),
    ACCOUNT("Просмотр"),
    ACCOUNT_DELETE("Удаление"),
    ACCOUNT_CHANGE("Изменение"),
    ACCOUNT_CREATE("Создание"),

    // Операции взаимодействия с пользователями
    USER_LIST("Просмотр списка"),
    USER("Просмотр"),
    USER_DELETE("Удаление"),
    USER_CHANGE("Изменение"),
    USER_CREATE("Создание"),

    // Операции взаимодействия с группами пользователей
    USER_GROUP_LIST("Просмотр списка"),
    USER_GROUP("Просмотр"),
    USER_GROUP_DELETE("Удаление"),
    USER_GROUP_CHANGE("Изменение"),
    USER_GROUP_CREATE("Создание");


    private final String text;

    UserOperation(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static UserOperation getByText(String text) {
        if (text == null)
            throw new NullPointerException("Text is null");

        for (UserOperation value : UserOperation.values())
            if (value.text.equals(text))
                return value;

        throw new IllegalArgumentException("No enum constant " + text);
    }

}
