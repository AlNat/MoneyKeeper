package dev.alnat.moneykeeper.dao;

import dev.alnat.moneykeeper.model.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {



}
