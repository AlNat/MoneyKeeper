package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.exception.MoneyKeeperNotFoundException;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.model.enums.TransactionStatusEnum;
import dev.alnat.moneykeeper.model.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    /**
     * Создание транзакции по набору параметров
     *
     * @param processDate дата проведения
     * @param amount сумма
     * @param status статус проводки
     * @param type тип проводки
     * @param comment комментарий
     * @param categoryName имя категории покупки (может быть null)
     * @param accountName имя счета, к которому относится транзакция
     * @throws MoneyKeeperException при ошибке
     * @throws MoneyKeeperNotFoundException если категория или счет по этому имени не найдены
     * @throws dev.alnat.moneykeeper.exception.MoneyKeeperIllegalArgumentException если логика заполнения полей будет некорректной
     */
    void create(LocalDateTime processDate, BigDecimal amount, TransactionStatusEnum status,
                TransactionTypeEnum type, String comment, String categoryName, String accountName)
            throws MoneyKeeperException;

    void update(Transaction transaction);

    void delete(Transaction transaction);

    void delete(Integer transactionID);

    /**
     * Поиск списка транзакций по аккаунту
     *
     * @param account аккаунт
     * @return список транзакций
     */
    List<Transaction> getTransactionsByAccount(Account account);

    /**
     * Поиск списка транзакций по аккаунту
     *
     * @param accountName имя аккаунта
     * @return список транзакций
     * @throws MoneyKeeperException при ошибке в работе
     * @throws MoneyKeeperNotFoundException Если сущность не найдена
     */
    List<Transaction> getTransactionsByAccountName(String accountName) throws MoneyKeeperException;

    /**
     * Поиск списка транзакций по аккаунту
     *
     * @param accountName имя аккаунта
     * @param from дата начала выборки
     * @param to дата окончания выборки
     * @return список транзакций
     * @throws MoneyKeeperException при ошибке в работе
     * @throws MoneyKeeperNotFoundException Если сущность не найдена
     */
    List<Transaction> getTransactionsByAccountName(String accountName, LocalDateTime from, LocalDateTime to) throws MoneyKeeperException;

    /**
     * Поиск списка транзакций по аккаунту
     *
     * @param accountName имя аккаунта
     * @param from дата начала выборки
     * @param to дата окончания выборки
     * @return список транзакций
     * @throws MoneyKeeperException при ошибке в работе
     * @throws MoneyKeeperNotFoundException Если сущность не найдена
     */
    List<Transaction> getTransactionsByAccountName(String accountName, LocalDate from, LocalDate to) throws MoneyKeeperException;

    /**
     * Метод получения списка транзакций по фильтру поиска
     *
     * @param filter фильтр поиска
     * @return список транзакций
     */
    List<Transaction> getTransactionByFilter(TransactionSearchFilter filter);

    List<Transaction> getAllTransaction();

}
