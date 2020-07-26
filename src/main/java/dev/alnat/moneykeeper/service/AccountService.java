package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.model.Account;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface AccountService {

    void create(Account account);

    void delete(Account account);

    void delete(Integer accountID);

    Account getAccountByName(String name);

}
