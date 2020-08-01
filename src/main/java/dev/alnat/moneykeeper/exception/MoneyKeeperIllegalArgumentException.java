package dev.alnat.moneykeeper.exception;

/**
 * Exception в случае если переданные в функцию аргументы не корректны
 *
 * Created by @author AlNat on 30.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class MoneyKeeperIllegalArgumentException extends MoneyKeeperException {


    public MoneyKeeperIllegalArgumentException(String message) {
        super(message);
    }

    public MoneyKeeperIllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public MoneyKeeperIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
