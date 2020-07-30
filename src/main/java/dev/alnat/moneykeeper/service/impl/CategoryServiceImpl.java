package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.CategoryRepository;
import dev.alnat.moneykeeper.model.Category;
import dev.alnat.moneykeeper.service.CategoryService;
import dev.alnat.moneykeeper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category get(Integer categoryID) {
        return categoryRepository.findById(categoryID).get(); // TODO Обработка специализированным Exception про NotFound
    }

    @Override
    public void create(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void create(String name, String description, String parentCategoryName) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);

        if (!StringUtil.isNullOrEmpty(parentCategoryName)) {
            Category parentCategory = categoryRepository.getCategoryByName(parentCategoryName);
            category.setParentCategory(parentCategory);
        }

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
    public Category getCategoryByName(String name) {
        return categoryRepository.getCategoryByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
