package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.AccountRepository;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account get(Integer accountID) {
        return accountRepository.findById(accountID).get(); // TODO Обработка специализированным Exception про NotFound
    }

    @Override
    public void create(Account account) {
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
    public Account getAccountByName(String name) {
        return accountRepository.getAccountByName(name);
    }

    @Override
    public List<Account> getAccountList() {
        return StreamSupport
                .stream(accountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
