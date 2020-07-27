package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.TransactionRepository;
import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void create(Transaction transaction) {
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
    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.getTransactionsByAccount(account);
    }

    @Override
    public List<Transaction> getTransactionByFilter(TransactionSearchFilter filter) {
        return transactionRepository.getTransactionsByFilter(filter);
    }

}
