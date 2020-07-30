package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.TransactionRepository;
import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Category;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.model.enums.TransactionStatusEnum;
import dev.alnat.moneykeeper.model.enums.TransactionTypeEnum;
import dev.alnat.moneykeeper.service.AccountService;
import dev.alnat.moneykeeper.service.CategoryService;
import dev.alnat.moneykeeper.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    private final CategoryService categoryService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountService accountService,
                                  CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }


    @Override
    public void create(Transaction transaction) {
        transactionRepository.create(transaction);
    }

    @Override
    public void create(LocalDateTime processDate, BigDecimal amount,
                       TransactionStatusEnum status, TransactionTypeEnum type,
                       String comment, String categoryName, String accountName) {
        Account account = accountService.getAccountByName(accountName);
        Category category = categoryService.getCategoryByName(categoryName);

        if (type == TransactionTypeEnum.ADDITION) {
            // TODO Проверка на неотрицательную сумму транзакции
        } else if (type == TransactionTypeEnum.SUBTRACTION) {
            // TODO Проверка на отрицательную сумму транзакции
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setComment(comment);
        transaction.setStatus(status == null ? TransactionStatusEnum.CONFORMED : status);
        transaction.setType(type);
        transaction.setProcessDate(processDate == null ? LocalDateTime.now() : processDate);

        transactionRepository.create(transaction);
    }

    @Override
    public Transaction get(Integer transactionID) {
        return transactionRepository.getByID(transactionID);  // TODO Обработка специализированным Exception про NotFound
    }

    @Override
    public void delete(Integer id) {
        transactionRepository.delete(id);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.getAll();
    }

    @Override
    public List<Transaction> getTransactionsByAccountName(String accountName) {
        Account account = accountService.getAccountByName(accountName);

        if (account == null) {
            // TODO Обработка специализированным Exception про NotFound
        }

        return transactionRepository.getTransactionsByAccount(account);
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.getTransactionsByAccount(account);
    }

    @Override
    public List<Transaction> getTransactionByFilter(TransactionSearchFilter filter) {
        return transactionRepository.getTransactionsByFilter(filter);
    }

}
