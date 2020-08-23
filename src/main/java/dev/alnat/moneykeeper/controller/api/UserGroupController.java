package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.model.UserGroup;
import dev.alnat.moneykeeper.model.enums.UserOperation;
import dev.alnat.moneykeeper.service.UserGroupService;
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
@Tag(name = "User Group API",
        description = "REST API для взаимодействия с группами пользователей")
@RestController
@RequestMapping(value = "/api/usergroup", produces = {"application/json", "application/xml"})
public class UserGroupController {

    private final UserGroupService userGroupService;

    @Autowired
    public UserGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }


    @Operation(summary = "Получение списка пользовательских групп")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER_GROUP_LIST')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserGroup> getUserGroupList() {
        return userGroupService.getUserGroupList();
    }


    @Operation(summary = "Получение группы по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Группы с таким идентификатором не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER_GROUP')")
    @RequestMapping(value = "/{userGroupID}", method = RequestMethod.GET)
    public Optional<UserGroup> getUserGroupByID(
            @Parameter(description = "Идентификатор группы", required = true, example = "1")
            @PathVariable
                    Integer userGroupID) {
        return userGroupService.get(userGroupID);
    }


    @Operation(summary = "Получение группы по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Группы с таким именем не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER_GROUP')")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Optional<UserGroup> getUserGroupByKey(
            @Parameter(description = "Идентификатор группы", required = true, example = "admin")
            @RequestParam
                    String userGroupKey) {
        return userGroupService.get(userGroupKey);
    }


    @Operation(summary = "Обновление группы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Группа успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_GROUP_CHANGE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updateUserGroup(
            @Parameter(description = "Измененная группа", required = true)
            @RequestBody
                    UserGroup userGroup) {
        userGroupService.update(userGroup);
    }


    @Operation(summary = "Создание новой группы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Группа успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_GROUP_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addUserGroup(
            @Parameter(description = "Новая группа", required = true)
            @RequestBody
                    UserGroup userGroup) {
        userGroupService.create(userGroup);
    }


    @Operation(summary = "Удаление группы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Группы с таким идентификатором не найдено"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_GROUP_DELETE')")
    @RequestMapping(value = "/{userGroupID}", method = RequestMethod.DELETE)
    public void deleteUserGroup(
            @Parameter(description = "Идентификатор группы", required = true, example = "1")
            @PathVariable
                    Integer userGroupID) {
        userGroupService.delete(userGroupID);
    }


    @Operation(summary = "Добавление возможности совершить операцию к группе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Добавление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Группа с таким идентификатором не найден"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_GROUP_CHANGE')")
    @RequestMapping(value = "/permission/", method = RequestMethod.POST)
    public void addPermissionToGroup(
            @Parameter(description = "Идентификатор группы", required = true, example = "1")
            @RequestParam
                    Integer userGroupID,
            @Parameter(description = "Операция для добавления", required = true, example = "ACCOUNT_LIST")
            @RequestParam
                    UserOperation operation) throws MoneyKeeperException {
        userGroupService.addPermission(userGroupID, operation);
    }


    @Operation(summary = "Удаление возможности совершить операцию к группе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Добавление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Группа с таким идентификатором не найден"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('USER_GROUP_CHANGE')")
    @RequestMapping(value = "/permission/", method = RequestMethod.DELETE)
    public void removePermissionFromGroup(
            @Parameter(description = "Идентификатор группы", required = true, example = "1")
            @RequestParam
                    Integer userGroupID,
            @Parameter(description = "Операция для удаления из группы", required = true, example = "ACCOUNT_LIST")
            @RequestParam
                    UserOperation operation) throws MoneyKeeperException {
        userGroupService.removePermission(userGroupID, operation);
    }


}
