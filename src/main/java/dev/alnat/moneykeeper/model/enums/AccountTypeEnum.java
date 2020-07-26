package dev.alnat.moneykeeper.model.enums;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public enum AccountTypeEnum {
    CASH("Кошелек (наличность)"),
    CARD("Карта");

    private final String text;

    AccountTypeEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static AccountTypeEnum getByText(String text) {
        if (text == null) {
            throw new NullPointerException("Text is null");
        }

        for (AccountTypeEnum value : AccountTypeEnum.values()) {
            if (value.text.equals(text)) {
                return value;
            }
        }

        throw new IllegalArgumentException("No enum like " + text);
    }

}
