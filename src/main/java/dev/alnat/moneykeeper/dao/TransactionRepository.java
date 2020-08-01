package dev.alnat.moneykeeper.dao;

import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface TransactionRepository {

    void create(Transaction transaction);

    void delete(Transaction transaction);

    void delete(Integer transactionID);

    Optional<Transaction> getByID(Integer transactionID);

    List<Transaction> getAll();

    /**
     * Получение списка транзакций по аккаунту
     *
     * @param account аккаунт
     * @return список транзакций по нему
     */
    List<Transaction> getTransactionsByAccount(Account account);

    /**
     * Получения списка транзакций по фильтру поиска
     *
     * @param filter фильтр поиска
     * @return список транзакций, которые подошли под фильтр
     */
    List<Transaction> getTransactionsByFilter(TransactionSearchFilter filter);

}
