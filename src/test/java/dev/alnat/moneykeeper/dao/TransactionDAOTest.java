package dev.alnat.moneykeeper.dao;

import dev.alnat.moneykeeper.model.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты репозитория по транзакциям
 * Тестировать саму SpringData смысла нет - тестируются только самописные методы
 * Причем это скорее интеграционный тест
 * <p>
 * TODO Доразбираться в этом!!!
 * TODO Сделать тестовую конфигурацию
 *
 * Created by @author AlNat on 05.08.2020.
 * Licensed by Apache License, Version 2.0
 */
//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TransactionDAOTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void initDBData() {
        Transaction t = new Transaction();
        t.setTransactionID(12345);

        testEntityManager.persist(t);
    }

//    @Test
    void testSaveAndGet() {
        Optional<Transaction> fromDb = transactionRepository.getByID(12345);

        assertTrue(fromDb.isPresent());
        assertEquals(fromDb.get().getTransactionID(), 12345);
    }

}
