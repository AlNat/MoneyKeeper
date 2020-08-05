package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.dao.TransactionRepository;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Тест-кейсы работы с сервисом по транзакциям
 * TODO Сделать тестовую конфигурацию
 *
 * Created by @author AlNat on 05.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransactionServiceTest {

    @MockBean
    private TransactionRepository repository;

    @Autowired
    TransactionService service;

    @Test
    @DisplayName("Получение сущности по идентификатору")
    public void testGetByID() {
        Transaction transaction = new Transaction();
        transaction.setTransactionID(12345);

        given(repository.getByID(any()))
                .willReturn(java.util.Optional.of(transaction));

        Optional<Transaction> result = service.get(12345);
        assertTrue(result.isPresent());
        assertEquals(result.get().getTransactionID(), 12345);
    }


}
