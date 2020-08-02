package dev.alnat.moneykeeper.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Небольшой wrapper над ошибочным ответом от API
 *
 * Created by @author AlNat on 01.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Schema(hidden = true)
public class APIError {

    @Schema(description = "Описание ошибки")
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
