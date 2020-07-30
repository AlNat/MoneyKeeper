package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.model.Category;
import dev.alnat.moneykeeper.service.CategoryService;
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
@SuppressWarnings("DefaultAnnotationParam")
@RestController
@RequestMapping(value = "/api/category", produces = {"application/json", "application/xml"})
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Operation(summary = "Получение списка категорий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Category> getCategoryList() {
        return categoryService.getAllCategory();
    }


    @Operation(summary = "Получение категории по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе", content = @Content),
            @ApiResponse(responseCode = "404", description = "Категории с таким идентификатором не найдено", content = @Content),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса", content = @Content)
    })
    @RequestMapping(value = "/{categoryID}", method = RequestMethod.GET)
    public Category getCategoryByID(
            @Parameter(description = "Идентификатор категории", required = true, example = "1")
            @PathVariable
                    Integer categoryID) {
        return categoryService.get(categoryID);
    }


    @Operation(summary = "Создание новой категории")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Категория успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addCategory(
            @Parameter(description = "Новая категория", required = true)
            @RequestBody
                    Category category) {
        categoryService.create(category);
    }


    @Operation(summary = "Создание новой категории по параметрам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Категория успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public void addCategory(
            @Parameter(description = "Имя категории", required = true, example = "Тестовая категория")
            @RequestParam
                    String name,
            @Parameter(description = "Описание категории", required = true, example = "Категория покупок для тестирования")
            @RequestParam
                    String description,
            @Parameter(description = "Имя вышестоящей категории", required = false, example = "Категория")
            @RequestParam
                    String parentCategoryName) {
        categoryService.create(name, description, parentCategoryName);
    }


    @Operation(summary = "Удаление категории")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление успешно выполнено"),
            @ApiResponse(responseCode = "400", description = "Запрос не корректен"),
            @ApiResponse(responseCode = "404", description = "Категории с таким идентификатором не найдено"),
            @ApiResponse(responseCode = "401", description = "Запрос не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обработки запроса")
    })
    @RequestMapping(value = "/{categoryID}", method = RequestMethod.DELETE)
    public void deleteCategory(
            @Parameter(description = "Идентификатор категории", required = true, example = "1")
            @PathVariable
                    Integer categoryID) {
        categoryService.delete(categoryID);
    }

}