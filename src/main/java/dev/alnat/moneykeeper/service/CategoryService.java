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

    Optional<Category> getCategoryByKey(String key);

    List<Category> getAllCategory();

    void create(Category category);

    void create(String key, String name, String description, String parentCategoryKey) throws MoneyKeeperNotFoundException;

    void update(Category category);

    void delete(Category category);

    void delete(Integer categoryID);

}
