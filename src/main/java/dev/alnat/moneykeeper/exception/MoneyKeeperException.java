package dev.alnat.moneykeeper.exception;

/**
 * Верхнеуровневый exception
 *
 * Created by @author AlNat on 30.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class MoneyKeeperException extends Exception {

    private static final long serialVersionUID = 82324325734783L;

    public MoneyKeeperException(String message) {
        super(message);
    }
    public MoneyKeeperException(Throwable cause) {
        super(cause);
    }

    public MoneyKeeperException(String message, Throwable cause) {
        super(message, cause);
    }


}


