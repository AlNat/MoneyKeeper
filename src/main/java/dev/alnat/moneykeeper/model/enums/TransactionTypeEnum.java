package dev.alnat.moneykeeper.model.enums;

/**
 * Created by @author AlNat on 21.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public enum TransactionTypeEnum {
    ADDITION("Пополнение"),
    SUBTRACTION("Трата"),
    MOVEMENT("Перемещение средств");

    private final String text;

    TransactionTypeEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static TransactionTypeEnum getByText(String text) {
        if (text == null) {
            throw new NullPointerException("Text is null");
        }

        for (TransactionTypeEnum value : TransactionTypeEnum.values()) {
            if (value.text.equals(text)) {
                return value;
            }
        }

        throw new IllegalArgumentException("No enum like " + text);
    }

}
