package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;

import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface AccountService {

    Optional<Account> get(Integer accountID);

    Optional<Account> getAccountByName(String name);

    List<Account> getAccountList();

    void create(Account account);

    void create(String name, String description, AccountTypeEnum type);

    void update(Account account);

    void delete(Account account);

    void delete(Integer accountID);

}
