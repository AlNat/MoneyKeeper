package dev.alnat.moneykeeper.exception;

/**
 * Exception в случае если сущность не найдена
 *
 * Created by @author AlNat on 27.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class MoneyKeeperNotFoundException extends MoneyKeeperException {


    public MoneyKeeperNotFoundException(String message) {
        super(message);
    }

    public MoneyKeeperNotFoundException(Throwable cause) {
        super(cause);
    }

    public MoneyKeeperNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
