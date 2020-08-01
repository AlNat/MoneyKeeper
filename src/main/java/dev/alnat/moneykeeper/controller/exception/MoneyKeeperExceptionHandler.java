package dev.alnat.moneykeeper.controller.exception;

import dev.alnat.moneykeeper.dto.APIError;
import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.exception.MoneyKeeperIllegalArgumentException;
import dev.alnat.moneykeeper.exception.MoneyKeeperNotFoundException;
import dev.alnat.moneykeeper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Глобальный перехватчик ошибок от контроллеров
 *
 * Created by @author AlNat on 30.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@ControllerAdvice
public class MoneyKeeperExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody APIError handleNullPointerException(NullPointerException e) {
        log.error("{}", StringUtil.getStackTrace(e));
        return APIError.of(e.getMessage());
    }

    @ExceptionHandler(MoneyKeeperIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody APIError handleIllegalArgument(MoneyKeeperIllegalArgumentException e) {
        log.error("{}", StringUtil.getStackTrace(e));
        return APIError.of(e.getMessage());
    }

    @ExceptionHandler(MoneyKeeperNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody APIError handleNotFoundException(MoneyKeeperNotFoundException e) {
        log.error("{}", StringUtil.getStackTrace(e));
        return APIError.of(e.getMessage());
    }

    @ExceptionHandler(MoneyKeeperException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody APIError handleException(MoneyKeeperException e) {
        log.error("{}", StringUtil.getStackTrace(e));
        return APIError.of(e.getMessage());
    }


    // Ничего не возвращаем в целях безопасности - эти ошибки не перехвачены выше, а значит что-то совсем не то
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleAllException(Exception e) {
        log.error("{}", StringUtil.getStackTrace(e));
    }

}
