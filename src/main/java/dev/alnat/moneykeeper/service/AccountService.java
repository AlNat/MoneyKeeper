package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.model.Account;

import java.util.List;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface AccountService {

    Account get(Integer accountID);

    void create(Account account);

    void delete(Account account);

    void delete(Integer accountID);

    Account getAccountByName(String name);

    List<Account> getAccountList();

}
