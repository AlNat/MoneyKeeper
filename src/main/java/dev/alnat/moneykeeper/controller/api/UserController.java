package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.model.User;
import dev.alnat.moneykeeper.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Tag(name = "User API",
        description = "REST API для взаимодействия с пользователями")
@RestController
@RequestMapping(value = "/api/user", produces = {"application/json", "application/xml"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Получение списка пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER_LIST')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList() {
        return userService.getUserList();
    }


    @Operation(summary = "Получение пользователя по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/{userID}", method = RequestMethod.GET)
    public Optional<User> getUserByID(
            @Parameter(description = "Идентификатор пользователя", required = true, example = "1")
            @PathVariable
                    Integer userID) {
        return userService.get(userID);
    }


    @Operation(summary = "Получение пользователя по имени")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователя с таким именем не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Optional<User> getUserByName(
            @Parameter(description = "Имя пользователя", required = true, example = "admin")
            @RequestParam
                    String userName) {
        return userService.get(userName);
    }


    @Operation(summary = "Обновление пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_CHANGE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updateUser(
            @Parameter(description = "Измененный пользователь", required = true)
            @RequestBody
                    User user) {
        userService.update(user);
    }


    @Operation(summary = "Создание нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addUser(
            @Parameter(description = "Новый пользователь", required = true)
            @RequestBody
                    User user) {
        userService.create(user);
    }


    @Operation(summary = "Удаление пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_DELETE')")
    @RequestMapping(value = "/{userID}", method = RequestMethod.DELETE)
    public void deleteUser(
            @Parameter(description = "Идентификатор пользователя", required = true, example = "1")
            @PathVariable
                    Integer userID) {
        userService.delete(userID);
    }


    @Operation(summary = "Добавление пользователя в группу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Добавление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_CHANGE')")
    @RequestMapping(value = "/group/", method = RequestMethod.POST)
    public void addUserToGroup(
            @Parameter(description = "Идентификатор пользователя", required = true, example = "1")
            @RequestParam
                    Integer userID,
            @Parameter(description = "Идентификатор группы", required = true, example = "1")
            @RequestParam
                    Integer userGroupID) throws MoneyKeeperException {
        userService.addToGroup(userID, userGroupID);
    }


    @Operation(summary = "Удаление пользователя из группы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_CHANGE')")
    @RequestMapping(value = "/group/", method = RequestMethod.DELETE)
    public void removeUserFromGroup(
            @Parameter(description = "Идентификатор пользователя", required = true, example = "1")
            @RequestParam
                    Integer userID,
            @Parameter(description = "Идентификатор группы", required = true, example = "1")
            @RequestParam
                    Integer userGroupID) throws MoneyKeeperException {
        userService.removeFromGroup(userID, userGroupID);
    }

}
