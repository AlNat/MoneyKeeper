package dev.alnat.moneykeeper.dao;

import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface AccountRepository extends CrudRepository<Account, Integer> {

    /**
     * Получение данных по счету по имени
     *
     * @param accountName имя счета
     * @return сам счет
     */
    Account getAccountByName(String accountName);

    /**
     * Получение списка счетов по типу
     *
     * @param type тип счета
     * @return список счетов с таким типом
     */
    List<Account> getAccountsByType(AccountTypeEnum type);

}
