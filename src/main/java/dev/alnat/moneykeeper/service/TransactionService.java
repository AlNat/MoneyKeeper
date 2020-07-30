package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.model.enums.TransactionStatusEnum;
import dev.alnat.moneykeeper.model.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface TransactionService {

    Transaction get(Integer transactionID);

    void create(Transaction transaction);

    void create(LocalDateTime processDate, BigDecimal amount, TransactionStatusEnum status,
                TransactionTypeEnum type, String comment, String categoryName, String accountName);

    void delete(Transaction transaction);

    void delete(Integer transactionID);

    /**
     * Поиск списка транзакций по аккаунту
     * @param account аккаунт
     * @return список транзакций
     */
    List<Transaction> getTransactionsByAccount(Account account);

    /**
     * Поиск списка транзакций по аккаунту
     * @param accountName имя аккаунта
     * @return список транзакций
     */
    List<Transaction> getTransactionsByAccountName(String accountName);

    /**
     * Метод получения списка транзакций по фильтру поиска
     *
     * @param filter фильтр поиска
     * @return список транзакций
     */
    List<Transaction> getTransactionByFilter(TransactionSearchFilter filter);

    List<Transaction> getAllTransaction();

}
