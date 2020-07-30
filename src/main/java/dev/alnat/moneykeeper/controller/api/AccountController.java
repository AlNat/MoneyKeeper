package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;
import dev.alnat.moneykeeper.service.AccountService;
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
@RequestMapping(value = "/api/account", produces = {"application/json", "application/xml"})
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @Operation(summary = "Получение списка счетов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Account> getAccountList() {
        return accountService.getAccountList();
    }


    @Operation(summary = "Получение счета по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Счет с таким идентификатором не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @RequestMapping(value = "/{accountID}", method = RequestMethod.GET)
    public Account getAccountByID(
            @Parameter(description = "Идентификатор счета", required = true, example = "1")
            @PathVariable
                    Integer accountID) {
        return accountService.get(accountID);
    }


    @Operation(summary = "Получение счета по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Счет с таким идентификатором не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Account getAccountByName(
            @Parameter(description = "Имя счет", required = true, example = "card")
            @RequestParam
                    String accountName) {
        return accountService.getAccountByName(accountName);
    }


    @Operation(summary = "Создание нового счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Счет успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addAccount(
            @Parameter(description = "Новый счет", required = true)
            @RequestBody
                    Account account) {
        accountService.create(account);
    }


    @Operation(summary = "Создание нового счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Счет успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public void addAccount(
            @Parameter(description = "Имя счета", required = true, example = "Тестовый счет")
            @RequestParam
                    String name,
            @Parameter(description = "Описание счета", required = false, example = "Тестовый счет для тестирования")
            @RequestParam
                    String description,
            @Parameter(description = "Тип счета", required = true, example = "CASH")
            @RequestParam
                    AccountTypeEnum type) {
        accountService.create(name, description, type);
    }


    @Operation(summary = "Удаление счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Счета с таким идентификатором не найдено"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @RequestMapping(value = "/{accountID}", method = RequestMethod.DELETE)
    public void deleteTransaction(
            @Parameter(description = "Идентификатор счета", required = true, example = "1")
            @PathVariable
                    Integer accountID) {
        accountService.delete(accountID);
    }

}
