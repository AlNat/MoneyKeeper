package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
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
    @RequestMapping(value = "/account/", method = RequestMethod.GET, params = "accountName")
    public List<Transaction> getTransactionList(
            @Parameter(description = "Имя счета", required = true, example = "card")
            @RequestParam
                    String accountName) {
        return transactionService.getTransactionsByAccountName(accountName);
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
    @RequestMapping(value = "/{transactionID}", method = RequestMethod.GET)
    public Transaction getTransactionByID(
            @Parameter(description = "Идентификатор транзакции", required = true, example = "1")
            @PathVariable
                    Integer transactionID) {
        return transactionService.get(transactionID);
    }


    @Operation(summary = "Создание новой транзакции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addTransaction(
            @Parameter(description = "Новая транзакция", required = true)
            @RequestBody
                    Transaction transaction) {
        transactionService.create(transaction);
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
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Transaction> getTransactionListByFilter(
            @Parameter(description = "Фильтр поиска", required = true, example = "123")
            @RequestBody
                    TransactionSearchFilter filter) {
        return transactionService.getTransactionByFilter(filter);
    }

}
