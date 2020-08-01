package dev.alnat.moneykeeper.controller.exception;

import dev.alnat.moneykeeper.dto.APIError;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

/**
 * Класс, который возвращает ошибку, если сущность optional не представлена
 *
 * Полезно для того, чтобы в контроллерах возвращать не саму сущность, а Optional
 * с ней внутри или пустой.
 * Данный класс обеспечивает этот процесс - перехватывает и возвращает саму сущность
 * или бросает ошибку 404
 *
 * Created by @author Александр Натаров on 01.08.2020
 * Licensed by Apache License, Version 2.0
 */
@SuppressWarnings("rawtypes")
@ControllerAdvice
public class OptionalExceptionHandler implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getParameterType().equals(Optional.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // Если это Optional - проверяем что он заполненн
        if (returnType.getParameterType().equals(Optional.class)) {
            Optional<?> optional = (Optional<?>) body;

            // Если нет сущности - возвращаем ошибку
            // Иначе саму сущность
            if (optional.isEmpty()) {
                response.setStatusCode(HttpStatus.NOT_FOUND);

                return APIError.of("Object not found!");
            } else {
                return optional.get();
            }
        }
        return body;
    }

}
