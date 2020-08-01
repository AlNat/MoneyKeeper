package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.exception.MoneyKeeperNotFoundException;
import dev.alnat.moneykeeper.model.Category;

import java.util.List;
import java.util.Optional;


/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface CategoryService {

    Optional<Category> get(Integer categoryID);

    void create(Category category);

    void create(String name, String description, String parentCategoryName) throws MoneyKeeperNotFoundException;

    void delete(Category category);

    void delete(Integer categoryID);

    Optional<Category> getCategoryByName(String name);

    List<Category> getAllCategory();

}
