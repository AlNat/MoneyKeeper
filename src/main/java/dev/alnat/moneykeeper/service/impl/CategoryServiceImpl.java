package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.CategoryRepository;
import dev.alnat.moneykeeper.exception.MoneyKeeperNotFoundException;
import dev.alnat.moneykeeper.model.Category;
import dev.alnat.moneykeeper.service.CategoryService;
import dev.alnat.moneykeeper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Optional<Category> get(Integer categoryID) {
        return categoryRepository.findById(categoryID);
    }

    @Override
    public void create(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void create(String key, String name, String description, String parentCategoryKey) throws MoneyKeeperNotFoundException {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);

        // Если есть родительская категория - пытаемся сделать связь
        if (!StringUtil.isNullOrEmpty(parentCategoryKey)) {
            Optional<Category> parentCategory = categoryRepository.findCategoryByKey(parentCategoryKey);
            if (parentCategory.isEmpty()) {
                log.error("Не найдено родительской категории с идентификатором {}!", parentCategoryKey);
                throw new MoneyKeeperNotFoundException("Не найдено родительской категории с переданным идентификатором!");
            }
            category.setParentCategory(parentCategory.get());
        }

        // Если нет идентификатора - генерируем его
        if (StringUtil.isNullOrEmpty(key)) {
            category.setKey(StringUtil.generateKey(name));
        }

        categoryRepository.save(category);
    }

    @Override
    public void update(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void delete(Integer categoryID) {
        categoryRepository.deleteById(categoryID);
    }

    @Override
    public Optional<Category> getCategoryByKey(String key) {
        return categoryRepository.findCategoryByKey(key);
    }

    @Override
    public List<Category> getAllCategory() {
        return StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
