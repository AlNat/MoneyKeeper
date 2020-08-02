package dev.alnat.moneykeeper.dao;

import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.dto.AccountBalance;
import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface AccountRepository extends CrudRepository<Account, Integer> {

    /**
     * Получение баланса по счету на каждый день
     *
     * @param accountID идентификатор счета
     * @return баланс по дням
     */
    @Query(name = "getDataFromAccountBalance", nativeQuery = true)
    List<AccountBalance> getAccountBalanceMap(@Param("accountID") Integer accountID);

    /**
     * Получение баланса по счету на каждый день
     *
     * @param accountID идентификатор счета
     * @param from дата начала выборки
     * @param to дата завершения выборки
     * @return баланс по дням
     */
    @Query(name = "getDataFromAccountBalanceWithDate", nativeQuery = true)
    List<AccountBalance> getAccountBalanceMap(@Param("accountID") Integer accountID,
                                              @Param("from") LocalDate from,
                                              @Param("to") LocalDate to);

    /**
     * Получение данных по счету по имени
     *
     * @param accountKey идентификатор счета
     * @return сам счет
     */
    @Cacheable(value = "account")
    Optional<Account> findAccountByKey(String accountKey);

    /**
     * Получение списка счетов по типу
     *
     * @param type тип счета
     * @return список счетов с таким типом
     */
    List<Account> getAccountsByType(AccountTypeEnum type);

}
