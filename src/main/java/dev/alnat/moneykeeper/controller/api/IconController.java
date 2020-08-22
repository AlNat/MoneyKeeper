package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.model.Icon;
import dev.alnat.moneykeeper.service.IconService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 02.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Tag(name = "Icon API",
        description = "REST API для взаимодействия с икноками категорий")
@RestController
@RequestMapping(value = "/api/icon")
public class IconController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IconService iconService;


    @Operation(summary = "Получение списка иконок")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('CATEGORY')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Icon> getIconList() {
        return iconService.getAllIcon();
    }


    @Operation(summary = "Получение иконки по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Икноки с таким идентификатором не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('CATEGORY')")
    @RequestMapping(value = "/{iconID}", method = RequestMethod.GET)
    public Optional<Icon> getIconByID(
            @Parameter(description = "Идентификатор иконки", required = true, example = "1")
            @PathVariable
                    Integer iconID) {
        return iconService.get(iconID);
    }


    @Operation(summary = "Обновление иконки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Иконка успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('CATEGORY_CHANGE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updatedIcon(
            @Parameter(description = "Измененная иконка", required = true)
            @RequestBody
                    Icon category) {
        iconService.create(category);
    }


    @Operation(summary = "Загрузка новой иконки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Иконка успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addIcon(
            @RequestParam("file")
                    MultipartFile icon) throws MoneyKeeperException {
        byte[] iconData;
        try {
            iconData = icon.getBytes();
        } catch (IOException | NullPointerException e) {
            log.error("Ошибка при загрузке файла с иконкой: {}!", e.getMessage());
            throw new MoneyKeeperException("Ошибка при загрузке файла с иконкой!", e);
        }

        iconService.create(iconData);
    }


    @Operation(summary = "Удаление иконки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Категории с таким идентификатором не найдено"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    @RequestMapping(value = "/{iconID}", method = RequestMethod.DELETE)
    public void deleteIcon(
            @Parameter(description = "Идентификатор иконки", required = true, example = "1")
            @PathVariable
                    Integer iconID) {
        iconService.delete(iconID);
    }


}
