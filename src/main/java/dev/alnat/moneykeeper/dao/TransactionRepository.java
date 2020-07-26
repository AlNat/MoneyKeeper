package dev.alnat.moneykeeper.dao;

import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.model.enums.TransactionTypeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO Переделать - принимать собственный, кастомный Фильтр поиска, по которому уже его и искать
 * Сделать через Qriteria Query
 *
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

    /**
     * Получение списка транзакций по аккаунту
     *
     * @param account
     * @return
     */
    List<Transaction> getTransactionsByAccount(Account account);

    List<Transaction> getTransactionsByAccountAndType(Account account, TransactionTypeEnum type);


    List<Transaction> getTransactionsByAccount_AccountID(Integer accountID);

    List<Transaction> getTransactionsByAccount_AccountID(Integer accountID, Pageable pageable);


    List<Transaction> getTransactionsByAccount_Name(String accountName);

    List<Transaction> getTransactionsByAccount_Name(String accountName, Pageable pageable);


    List<Transaction> getTransactionsByProcessDateBetween(LocalDateTime from, LocalDateTime to);

    List<Transaction> getTransactionsByProcessDateBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    @Query(value = "select t FROM transaction t WHERE t.status = 0 AND " +
            "cast(t.processdate as date) >= :from AND cast(t.processdate as date) <= :to", nativeQuery = true)
    List<Transaction> getActiveTransactionsByProcessDateBetween(LocalDate from, LocalDate to);

}
