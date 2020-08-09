package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.AccountRepository;
import dev.alnat.moneykeeper.dto.AccountInfo;
import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.exception.MoneyKeeperNotFoundException;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.dto.AccountBalance;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;
import dev.alnat.moneykeeper.service.AccountService;
import dev.alnat.moneykeeper.service.TransactionService;
import dev.alnat.moneykeeper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;


    @Override
    public Optional<Account> get(Integer accountID) {
        return accountRepository.findById(accountID);
    }


    @Override
    public AccountInfo getAccountInfo(Integer accountID, LocalDate from, LocalDate to) throws MoneyKeeperException {
        Optional<Account> account = accountRepository.findById(accountID);
        if (account.isEmpty()) {
            log.error("Нет счета с идентификатором #{}!", accountID);
            throw new MoneyKeeperNotFoundException("Нет счета с указанным идентификатором!");
        }

        return generateInfo(account.get(), from, to);
    }

    @Override
    public AccountInfo getAccountInfo(String accountKey, LocalDate from, LocalDate to) throws MoneyKeeperException {
        Optional<Account> account = accountRepository.findAccountByKey(accountKey);
        if (account.isEmpty()) {
            log.error("Нет счета с идентификатором {}!", accountKey);
            throw new MoneyKeeperNotFoundException("Нет счета с указанным идентификатором!");
        }

        return generateInfo(account.get(), from, to);
    }

    private AccountInfo generateInfo(Account account, LocalDate from, LocalDate to) throws MoneyKeeperException {
        // Получаем баланс
        List<AccountBalance> balanceList = accountRepository
                .getAccountBalanceMap(account.getAccountID(), from, to)
                .stream()
                .sorted(Comparator.comparing(AccountBalance::getDate))
                .collect(Collectors.toList());

        // Получаем список транзакций
        List<Transaction> transactionList = transactionService
                .getTransactionsByAccountKey(account.getKey(), from, to)
                .stream()
                .sorted(Comparator.comparing(Transaction::getProcessDate))
                .collect(Collectors.toList());

        return AccountInfo.builder()
                .fromAccount(account)
                .from(from)
                .balance(balanceList)
                .withTransactionList(transactionList)
                .to(to)
                .build();
    }

    @Override
    public void create(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void create(String key, String name, String description, AccountTypeEnum type) {
        Account account = new Account();
        account.setName(name);
        account.setDescription(description);
        account.setType(type);

        // Если нет идентификатора - генерируем его
        if (StringUtil.isNullOrEmpty(key)) {
            account.setKey(StringUtil.generateKey(name));
        }

        accountRepository.save(account);
    }

    @Override
    public void update(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public void delete(Integer accountID) {
        accountRepository.deleteById(accountID);
    }

    @Override
    public Optional<Account> getAccountByKey(String key) {
        return accountRepository.findAccountByKey(key);
    }

    @Override
    public List<Account> getAccountList() {
        return StreamSupport
                .stream(accountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
