package dev.alnat.moneykeeper.model.enums;

/**
 * Created by @author AlNat on 21.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public enum TransactionStatusEnum {
    CONFORMED("Проведена"),
    ROLLBACK("Отменена");

    private final String text;

    TransactionStatusEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static TransactionStatusEnum getByText(String text) {
        if (text == null) {
            throw new NullPointerException("Text is null");
        }

        for (TransactionStatusEnum value : TransactionStatusEnum.values()) {
            if (value.text.equals(text)) {
                return value;
            }
        }

        throw new IllegalArgumentException("No enum like " + text);
    }

}
