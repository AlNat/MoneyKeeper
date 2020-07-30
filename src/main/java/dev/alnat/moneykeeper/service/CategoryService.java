package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.model.Category;

import java.util.List;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface CategoryService {

    Category get(Integer categoryID);

    void create(Category category);

    void create(String name, String description, String parentCategoryName);

    void delete(Category category);

    void delete(Integer categoryID);

    Category getCategoryByName(String name);

    List<Category> getAllCategory();

}
