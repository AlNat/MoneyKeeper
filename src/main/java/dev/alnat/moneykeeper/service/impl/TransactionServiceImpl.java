package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.TransactionRepository;
import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.exception.MoneyKeeperIllegalArgumentException;
import dev.alnat.moneykeeper.exception.MoneyKeeperNotFoundException;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Category;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.model.enums.TransactionStatusEnum;
import dev.alnat.moneykeeper.model.enums.TransactionTypeEnum;
import dev.alnat.moneykeeper.service.AccountService;
import dev.alnat.moneykeeper.service.CategoryService;
import dev.alnat.moneykeeper.service.TransactionService;
import dev.alnat.moneykeeper.util.BigDecimalUtil;
import dev.alnat.moneykeeper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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


    // Вообще не очень хорошо использовать Entity как DTO
    // но, зачастую, необходимо, так что проверяем по максимуму все
    @Override
    public void create(Transaction transaction) throws MoneyKeeperException {
        // Проверяем что в переданной сущности заполнен Аккаунт
        if (transaction.getAccount() == null) {
            log.error("При сохранении транзакции не передан аккаунт!");
            throw new MoneyKeeperIllegalArgumentException("При сохранении транзакции не передан аккаунт!");
        }

        // Если он есть, но нет PK - пытаемся его найти
        if (transaction.getAccount().getAccountID() == null) {
            // Если нет имени - то тоже все плохо
            if (StringUtil.isNullOrEmpty(transaction.getAccount().getName())) {
                log.error("При сохранении транзакции не передано имя счета!");
                throw new MoneyKeeperIllegalArgumentException("При сохранении транзакции не передано имя счета!");
            }
            transaction.setAccount(
                    accountService.getAccountByName(transaction.getAccount().getName())
                    .orElseThrow(() -> {
                        log.error("При сохранении транзакции передано несуществующее имя счета: {}!",
                                transaction.getAccount().getName());
                        return new MoneyKeeperIllegalArgumentException("При сохранении транзакции передано несуществующее имя счета!");
                    })
            );
        }

        // Проверяем что в переданной сущности заполнена категория
        if (transaction.getCategory() != null && transaction.getCategory().getCategoryID() == null) {
            if (StringUtil.isNullOrEmpty(transaction.getCategory().getName())) {
                log.error("При сохранении транзакции не передано имя категории!");
                throw new MoneyKeeperIllegalArgumentException("При сохранении транзакции не передано имя категории!");
            }
            transaction.setCategory(
                    categoryService.getCategoryByName(transaction.getCategory().getName())
                    .orElseThrow(() -> {
                            log.error("При сохранении транзакции передано несуществующее имя категории: {}!",
                                    transaction.getAccount().getName());
                            return new MoneyKeeperIllegalArgumentException("При сохранении транзакции передано несуществующее имя категории");
                        }
                    ));
        }


        transactionRepository.create(transaction);
    }

    @Override
    public void create(LocalDateTime processDate, BigDecimal amount,
                       TransactionStatusEnum status, TransactionTypeEnum type,
                       String comment, String categoryName, String accountName) throws MoneyKeeperException {
        Optional<Account> account = accountService.getAccountByName(accountName);
        if (account.isEmpty()) {
            log.error("При сохранении транзакции передано несуществующее имя счета: {}", accountName);
            throw new MoneyKeeperNotFoundException("При сохранении транзакции передано несуществующее имя счета!");
        }

        Category category = null;
        if (!StringUtil.isNullOrEmpty(categoryName)) {
            Optional<Category> c = categoryService.getCategoryByName(categoryName);
            category = c.orElse(null);
        }

        if (type == TransactionTypeEnum.ADDITION) {
            if (!BigDecimalUtil.isPositiveOrZero(amount)) {
                log.error("Нельзя добавить отрицательную сумму на счет! Это необходимо сделать списанием! Сумма: {}", amount.toPlainString());
                throw new MoneyKeeperIllegalArgumentException("Нельзя добавить отрицательную сумму на счет!");
            }
        } else if (type == TransactionTypeEnum.SUBTRACTION) {
            if (!BigDecimalUtil.isNegative(amount)) {
                log.error("Нельзя списать положительную сумму со счета! Это необходимо сделать добавлением! Сумма: {}", amount.toPlainString());
                throw new MoneyKeeperIllegalArgumentException("Нельзя списать положительную сумму со счета!");
            }
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account.get());
        transaction.setCategory(category);
        transaction.setComment(comment);
        transaction.setStatus(status == null ? TransactionStatusEnum.CONFORMED : status);
        transaction.setType(type);
        transaction.setProcessDate(processDate == null ? LocalDateTime.now() : processDate);

        transactionRepository.create(transaction);
    }

    @Override
    public Optional<Transaction> get(Integer transactionID) {
        return transactionRepository.getByID(transactionID);
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
    public List<Transaction> getTransactionsByAccountName(String accountName) throws MoneyKeeperException {
        Optional<Account> account = accountService.getAccountByName(accountName);

        if (account.isEmpty()) {
            log.error("Ошибка при получении списка транзакций по счету {} - счета с таким именем нет!", account);
            throw new MoneyKeeperNotFoundException("Нет счета с таким именем!");
        }

        return transactionRepository.getTransactionsByAccount(account.get());
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
