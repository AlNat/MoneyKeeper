package dev.alnat.moneykeeper.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import dev.alnat.moneykeeper.model.abstracts.CreatedUpdated;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Entity
@Table(name = "category")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "categoryID", scope = Category.class)
@Schema(description = "Категория покупки")
public class Category extends CreatedUpdated implements Serializable {

    private static final long serialVersionUID = 5521315512322L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryID;

    @Column(nullable = false, unique = true)
    @NotNull
    @Schema(description = "Идентификатор категории", required = true)
    private String key;

    @Column(nullable = false)
    @NotNull
    @Schema(description = "Имя категории", required = true)
    private String name;

    @Column
    @Schema(description = "Описание категории", required = false)
    private String description;

    @JacksonXmlElementWrapper(localName = "categoryList")
    @JacksonXmlProperty(localName = "category")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @Schema(description = "Список покупок с этой категорией", required = false)
    private List<Transaction> transactionList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "parentcategory_id", nullable = true)
    @Schema(description = "Вышестоящая категория", required = false)
    private Category parentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "iconid", nullable = true)
    @Schema(description = "Иконка категории", required = false)
    private Icon icon;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    @Schema(description = "Список под-категорий", required = false)
    private List<Category> subCategoryList;


    public Category() {
        this.subCategoryList = new ArrayList<>();
    }


    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public List<Category> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<Category> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryID, category.categoryID) &&
                Objects.equals(key, category.key) &&
                Objects.equals(name, category.name) &&
                Objects.equals(description, category.description) &&
                Objects.equals(transactionList, category.transactionList) &&
                Objects.equals(parentCategory, category.parentCategory) &&
                Objects.equals(icon, category.icon) &&
                Objects.equals(subCategoryList, category.subCategoryList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID, key, name, description, transactionList, parentCategory, subCategoryList);
    }

}
