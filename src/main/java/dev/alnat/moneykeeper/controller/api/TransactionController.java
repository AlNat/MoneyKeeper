package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.model.enums.TransactionStatusEnum;
import dev.alnat.moneykeeper.model.enums.TransactionTypeEnum;
import dev.alnat.moneykeeper.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Tag(name = "Transaction API",
        description = "REST API для взаимодействия с проводками (покупками и пополнениями)")
@SuppressWarnings("DefaultAnnotationParam")
@RestController
@RequestMapping(value = "/api/transaction", produces = {"application/json", "application/xml"})
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @Operation(summary = "Получение списка транзакции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('TRANSACTION_LIST')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Transaction> getTransactionList() {
        return transactionService.getAllTransaction();
    }


    @Operation(summary = "Получение списка транзакции по счету")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('TRANSACTION_LIST')")
    @RequestMapping(value = "/account/", method = RequestMethod.GET, params = "accountKey")
    public List<Transaction> getTransactionList(
            @Parameter(description = "Идентификатор счета", required = true, example = "card")
            @RequestParam
                    String accountKey) throws MoneyKeeperException {
        return transactionService.getTransactionsByAccountKey(accountKey);
    }


    // Пришлось разнести по разным endpoint, т.к. народ со Spring-Doc не хочет чинить неработающую перегрузку
    // https://github.com/springdoc/springdoc-openapi/issues/15
    @Operation(summary = "Получение списка транзакции по счету")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('TRANSACTION_LIST')")
    @RequestMapping(value = "/account/datetime", method = RequestMethod.GET, params = {"accountKey", "from", "to"})
    public List<Transaction> getTransactionList(
            @Parameter(description = "Имя счета", required = true, example = "card")
            @RequestParam
                    String accountKey,
            @Parameter(description = "Дата и время начала выборки", required = true, example = "2020-01-01 00:00:01")
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                    LocalDateTime from,
            @Parameter(description = "Дата и время завершения выборки", required = true, example = "2020-01-01 23:59:59")
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                    LocalDateTime to) throws MoneyKeeperException {
        return transactionService.getTransactionsByAccountKey(accountKey, from, to);
    }


    // Пришлось разнести по разным endpoint, т.к. народ со Spring-Doc не хочет чинить неработающую перегрузку
    // https://github.com/springdoc/springdoc-openapi/issues/15
    @Operation(summary = "Получение списка транзакции по счету")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('TRANSACTION_LIST')")
    @RequestMapping(value = "/account/date", method = RequestMethod.GET, params = {"accountKey", "dateFrom", "dateTo"})
    public List<Transaction> getTransactionList(
            @Parameter(description = "Имя счета", required = true, example = "card")
            @RequestParam
                    String accountKey,
            @Parameter(description = "Дата начала выборки", required = true, example = "2020-01-01")
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate dateFrom,
            @Parameter(description = "Дата завершения выборки", required = true, example = "2020-01-02",
                    schema = @Schema)
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate dateTo) throws MoneyKeeperException {
        return transactionService.getTransactionsByAccountKey(accountKey, dateFrom, dateTo);
    }


    @Operation(summary = "Получение транзакции по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Транзакции с таким идентификатором не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('TRANSACTION')")
    @RequestMapping(value = "/{transactionID}", method = RequestMethod.GET)
    public Optional<Transaction> getTransactionByID(
            @Parameter(description = "Идентификатор транзакции", required = true, example = "1")
            @PathVariable
                    Integer transactionID) {
        return transactionService.get(transactionID);
    }


    @Operation(summary = "Обновление транзакции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Транзакция успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('TRANSACTION_CHANGE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updateTransaction(
            @Parameter(description = "Обновленная транзакция", required = true)
            @RequestBody
                    Transaction transaction) {
        transactionService.update(transaction);
    }


    @Operation(summary = "Создание новой транзакции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Транзакция успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('TRANSACTION_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addTransaction(
            @Parameter(description = "Новая транзакция", required = true)
            @RequestBody
                    Transaction transaction) throws MoneyKeeperException {
        transactionService.create(transaction);
    }


    @Operation(summary = "Создание новой транзакции по параметрам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Транзакция успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('TRANSACTION_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public void addTransaction(
            @Parameter(description = "Дата и время проведения", required = false, example = "2020-01-01 12:00:00")
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                    LocalDateTime processDate,
            @Parameter(description = "Сумма операции", required = true, example = "100.00")
            @RequestParam
                BigDecimal amount,
            @Parameter(description = "Статус операции", required = false, example = "CONFORMED")
            @RequestParam
                TransactionStatusEnum status,
            @Parameter(description = "Тип операции", required = true, example = "SUBTRACTION")
            @RequestParam
                TransactionTypeEnum type,
            @Parameter(description = "Комментарий", required = false, example = "Тестовая покупка")
            @RequestParam
                String comment,
            @Parameter(description = "Имя категории", required = false, example = "TEST")
            @RequestParam
                String categoryName,
            @Parameter(description = "Имя счета", required = true, example = "TEST")
            @RequestParam
                String accountKey) throws MoneyKeeperException {
        transactionService.create(processDate, amount, status, type, comment, categoryName, accountKey);
    }


    @Operation(summary = "Удаление транзакции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Транзакции с таким идентификатором не найдено"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('TRANSACTION_DELETE')")
    @RequestMapping(value = "/{transactionID}", method = RequestMethod.DELETE)
    public void deleteTransaction(
            @Parameter(description = "Идентификатор транзакции", required = true, example = "1")
            @PathVariable
                    Integer transactionID) {
        transactionService.delete(transactionID);
    }


    @Operation(summary = "Получение списка транзакций по фильтру поиска")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поиск успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в фильтре поиска", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('TRANSACTION_LIST')")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Transaction> getTransactionListByFilter(
            @Parameter(description = "Фильтр поиска", required = true, example = "123")
            @RequestBody
                    TransactionSearchFilter filter) {
        return transactionService.getTransactionByFilter(filter);
    }

}
