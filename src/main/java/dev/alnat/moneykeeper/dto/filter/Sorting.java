package dev.alnat.moneykeeper.dto.filter;

/**
 * Список сортировок в фильтре
 *
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class Sorting {

    private String sortBy;
    private SortOrder sortOrder;

    public Sorting() {
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Тип сортировки
     */
    public enum SortOrder {
        ASC, DESC;
    }

}

