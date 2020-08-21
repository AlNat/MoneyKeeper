package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.dto.AccountInfo;
import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;
import dev.alnat.moneykeeper.service.AccountService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Tag(name = "Account API",
        description = "REST API для взаимодействия со счетами")
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
    @PreAuthorize("hasAuthority('ACCOUNT_LIST')")
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
    @PreAuthorize("hasAuthority('ACCOUNT')")
    @RequestMapping(value = "/{accountID}", method = RequestMethod.GET)
    public Optional<Account> getAccountByID(
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
    @PreAuthorize("hasAuthority('ACCOUNT')")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Optional<Account> getAccountByKey(
            @Parameter(description = "Идентификатор счета", required = true, example = "card")
            @RequestParam
                    String accountKey) {
        return accountService.getAccountByKey(accountKey);
    }


    @Operation(summary = "Обновление информации по счету")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Счет успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('ACCOUNT')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updatedAccount(
            @Parameter(description = "Измененный счет", required = true)
            @RequestBody
                    Account account) {
        accountService.update(account);
    }


    @Operation(summary = "Создание нового счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Счет успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('ACCOUNT_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addAccount(
            @Parameter(description = "Новый счет", required = true)
            @RequestBody
                    Account account) {
        accountService.create(account);
    }


    @Operation(summary = "Создание нового счета по параметрам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Счет успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('ACCOUNT_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public void addAccount(
            @Parameter(description = "Идентификатор счета", required = false, example = "test_account")
            @RequestParam(required = false)
                    String key,
            @Parameter(description = "Имя счета", required = true, example = "Тестовый счет")
            @RequestParam
                    String name,
            @Parameter(description = "Описание счета", required = false, example = "Тестовый счет для тестирования")
            @RequestParam(required = false)
                    String description,
            @Parameter(description = "Тип счета", required = true, example = "CASH")
            @RequestParam
                    AccountTypeEnum type) {
        accountService.create(key, name, description, type);
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
    @PreAuthorize("hasAuthority('ACCOUNT_DELETE')")
    @RequestMapping(value = "/{accountID}", method = RequestMethod.DELETE)
    public void deleteAccount(
            @Parameter(description = "Идентификатор счета", required = true, example = "1")
            @PathVariable
                    Integer accountID) {
        accountService.delete(accountID);
    }


    @Operation(summary = "Получение агрегированной информации по счету")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Счет с таким идентификатором не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('ACCOUNT')")
    @RequestMapping(value = "/info/{accountID}", params = "!accountKey", method = RequestMethod.GET)
    public AccountInfo getAccountInfo(
            @Parameter(description = "Идентификатор счета", required = true, example = "1")
            @PathVariable
                    Integer accountID,
            @Parameter(description = "Дата начала выборки", required = true, example = "2020-01-01")
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate from,
            @Parameter(description = "Дата завершения выборки", required = true, example = "2020-01-02")
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate to) throws MoneyKeeperException {
        return accountService.getAccountInfo(accountID, from, to);
    }

    @Operation(summary = "Получение агрегированной информации по счету")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Счет с таким идентификатором не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('ACCOUNT')")
    @RequestMapping(value = "/info", params = "!accountID", method = RequestMethod.GET)
    public AccountInfo getAccountInfo(
            @Parameter(description = "Идентификатор счета", required = true, example = "1")
            @RequestParam
                    String accountKey,
            @Parameter(description = "Дата начала выборки", required = true, example = "2020-01-01")
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate from,
            @Parameter(description = "Дата завершения выборки", required = true, example = "2020-01-02")
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate to) throws MoneyKeeperException {
        return accountService.getAccountInfo(accountKey, from, to);
    }


}
