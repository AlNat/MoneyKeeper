package dev.alnat.moneykeeper.dto;

/**
 * Небольшой wrapper над ошибочным ответом от API
 *
 * Created by @author AlNat on 01.08.2020.
 * Licensed by Apache License, Version 2.0
 */
public class APIError {

    public String error;

    public APIError() {
    }

    public APIError(String error) {
        this.error = error;
    }

    public static APIError of(String error) {
        return new APIError(error);
    }


}
