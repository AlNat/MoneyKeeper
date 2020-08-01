package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.exception.MoneyKeeperNotFoundException;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.model.enums.TransactionStatusEnum;
import dev.alnat.moneykeeper.model.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface TransactionService {

    Optional<Transaction> get(Integer transactionID);

    void create(Transaction transaction) throws MoneyKeeperException;

    void create(LocalDateTime processDate, BigDecimal amount, TransactionStatusEnum status,
                TransactionTypeEnum type, String comment, String categoryName, String accountName)
            throws MoneyKeeperException;

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
     * @throws MoneyKeeperException при ошибке в работе
     * @throws MoneyKeeperNotFoundException Если сущность не найдена
     */
    List<Transaction> getTransactionsByAccountName(String accountName) throws MoneyKeeperException;

    /**
     * Метод получения списка транзакций по фильтру поиска
     *
     * @param filter фильтр поиска
     * @return список транзакций
     */
    List<Transaction> getTransactionByFilter(TransactionSearchFilter filter);

    List<Transaction> getAllTransaction();

}
