package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Transaction;

import java.util.List;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface TransactionService {

    Transaction get(Integer transactionID);

    void create(Transaction transaction);

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
