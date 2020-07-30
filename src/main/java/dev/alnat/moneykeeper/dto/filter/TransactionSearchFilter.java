package dev.alnat.moneykeeper.dto.filter;

import dev.alnat.moneykeeper.model.enums.TransactionStatusEnum;
import dev.alnat.moneykeeper.model.enums.TransactionTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Специальный фильтр поиск по транзакциями
 * Удобен тем, что передав они фильтр можно сразу получить полную выборку по транзакциями
 *
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class TransactionSearchFilter {

    private Integer accountID;

    private List<String> accountNameList;

    private List<String> categoriesNameList;

    private TransactionStatusEnum status;

    private TransactionTypeEnum type;

    private LocalDateTime from;

    private LocalDateTime to;

    private List<Sorting> sortingList;


    public TransactionSearchFilter() {
    }

    private TransactionSearchFilter(Builder builder) {
        setAccountID(builder.accountID);
        setAccountNameList(builder.accountNameList);
        setCategoriesNameList(builder.categoriesNameList);
        setStatus(builder.status);
        setType(builder.type);
        setFrom(builder.from);
        setTo(builder.to);
        setSortingList(builder.sortingList);
    }


    public static Builder builder() {
        return new Builder();
    }


    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public List<String> getAccountNameList() {
        return accountNameList;
    }

    public void setAccountNameList(List<String> accountNameList) {
        this.accountNameList = accountNameList;
    }

    public List<String> getCategoriesNameList() {
        return categoriesNameList;
    }

    public void setCategoriesNameList(List<String> categoriesNameList) {
        this.categoriesNameList = categoriesNameList;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public TransactionTypeEnum getType() {
        return type;
    }

    public void setType(TransactionTypeEnum type) {
        this.type = type;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public void setSortingList(List<Sorting> sortingList) {
        this.sortingList = sortingList;
    }

    public List<Sorting> getSortingList() {
        return sortingList;
    }


    /**
     * Билдер для удобства формирования запросов
     */
    public static final class Builder {
        private Integer accountID;
        private List<String> accountNameList;
        private List<String> categoriesNameList;
        private TransactionStatusEnum status;
        private TransactionTypeEnum type;
        private LocalDateTime from;
        private LocalDateTime to;
        private List<Sorting> sortingList;

        private Builder() {
        }

        public Builder accountID(Integer val) {
            accountID = val;
            return this;
        }

        public Builder accountNameList(List<String> val) {
            accountNameList = val;
            return this;
        }

        public Builder categoriesNameList(List<String> val) {
            categoriesNameList = val;
            return this;
        }

        public Builder status(TransactionStatusEnum val) {
            status = val;
            return this;
        }

        public Builder type(TransactionTypeEnum val) {
            type = val;
            return this;
        }

        public Builder from(LocalDateTime val) {
            from = val;
            return this;
        }

        public Builder to(LocalDateTime val) {
            to = val;
            return this;
        }

        public Builder sortingList(List<Sorting> val) {
            sortingList = val;
            return this;
        }

        public TransactionSearchFilter build() {
            return new TransactionSearchFilter(this);
        }
    }

}
