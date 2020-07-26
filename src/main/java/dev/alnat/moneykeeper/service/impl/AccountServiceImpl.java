package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.AccountRepository;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

}
