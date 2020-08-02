package dev.alnat.moneykeeper.dao;

import dev.alnat.moneykeeper.model.Category;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    /**
     * Поиск категории по идентификатору
     *
     * @param categoryKey имя категории
     * @return категория
     */
    @Cacheable(value = "category")
    Optional<Category> findCategoryByKey(String categoryKey);

    /**
     * Поиск категории по имени
     *
     * @param categoryName имя категории
     * @return категория
     */
    Optional<Category> findCategoryByName(String categoryName);

}
