package dev.alnat.moneykeeper.dao.impl;

import dev.alnat.moneykeeper.dao.TransactionRepository;
import dev.alnat.moneykeeper.dto.filter.Sorting;
import dev.alnat.moneykeeper.dto.filter.TransactionSearchFilter;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Category;
import dev.alnat.moneykeeper.model.Transaction;
import dev.alnat.moneykeeper.model.enums.TransactionStatusEnum;
import dev.alnat.moneykeeper.model.enums.TransactionTypeEnum;
import dev.alnat.moneykeeper.util.StringUtil;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public TransactionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void create(Transaction transaction) {
        entityManager.persist(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        entityManager.remove(transaction);
    }

    @Override
    public void delete(Integer transactionID) {
        entityManager
                .createQuery("DELETE FROM Transaction WHERE transactionID = :id")
                .setParameter("id", transactionID)
                .setHint(QueryHints.READ_ONLY, true)
                .executeUpdate();
    }

    @Override
    public Transaction getByID(Integer transactionID) {
        return entityManager.find(Transaction.class, transactionID);
    }

    @Override
    public List<Transaction> getAll() {
        return entityManager
                .createQuery("SELECT t FROM Transaction t", Transaction.class)
                .setHint(QueryHints.READ_ONLY, true)
                .getResultList();
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        return entityManager
                .createQuery("SELECT t FROM Transaction t WHERE t.account = :account", Transaction.class)
                .setParameter("account", account)
                .setHint(QueryHints.READ_ONLY, true)
                .getResultList();
    }

    @Override
    public List<Transaction> getTransactionsByFilter(TransactionSearchFilter filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> transactionCriteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> root = transactionCriteriaQuery.from(Transaction.class); // Откуда будут браться данные
        List<Predicate> conditions = new ArrayList<>(); // Набор условий
        List<Order> orderList = new ArrayList<>(); // Набор для сортировки


        // Если есть фильтр по категориям
        if (filter.getCategoriesNameList() != null && !filter.getCategoriesNameList().isEmpty()) {
            Join<Transaction, Category> categoryJoin = root.join("account", JoinType.LEFT); // Join таблиц Transaction и Category
            conditions.add(categoryJoin.<String>get("name").in(filter.getCategoriesNameList()));
        }

        // Если есть фильтрация по номеру кошелька или по списку имен кошельков
        if (filter.getAccountID() != null || (filter.getAccountNameList() != null && !filter.getAccountNameList().isEmpty())) {
            Join<Transaction, Account> accountJoin = root.join("account", JoinType.LEFT); // Join таблиц Transaction и Account
            // Ключ он подберет сам, но на всякий случай - transactionJoin.on(criteriaBuilder.equal(root.get("account"), transactionJoin.get("accountID")));

            if (filter.getAccountID() != null) {
                conditions.add(criteriaBuilder.equal(accountJoin.<Integer>get("accountID"), filter.getAccountID()));
            }

            if (filter.getAccountNameList() != null && !filter.getAccountNameList().isEmpty()) {
                conditions.add(accountJoin.<String>get("name").in(filter.getAccountNameList()));
            }
        }

        // Статус транзакции
        if (filter.getStatus() != null) {
            conditions.add(criteriaBuilder.equal(root.<TransactionStatusEnum>get("status"), filter.getStatus()));
        }

        // Тип транзакции
        if (filter.getType() != null) {
            conditions.add(criteriaBuilder.equal(root.<TransactionTypeEnum>get("type"), filter.getType()));
        }

        // Дата начала выборки
        if (filter.getFrom() != null) {
            conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.<LocalDateTime>get("processDate"), filter.getFrom()));
        }

        // Дата окончания выборки
        if (filter.getTo() != null) {
            conditions.add(criteriaBuilder.lessThanOrEqualTo(root.<LocalDateTime>get("processDate"), filter.getTo()));
        }


        CriteriaQuery<Transaction> criteriaQuery = transactionCriteriaQuery.select(root); // Откуда выбирать данные

        Expression<?> orderExpression;
        Order order = null;
        if (filter.getSortingList() != null && filter.getSortingList().size() > 0) {
            for (Sorting sorting : filter.getSortingList()) {
                if (StringUtil.isNullOrEmpty(sorting.getSortBy()))  continue;
                orderExpression = root.get(sorting.getSortBy());

                if (sorting.getSortOrder() == null || sorting.getSortOrder() == Sorting.SortOrder.ASC) {
                    order = criteriaBuilder.asc(orderExpression);
                } else {
                    order = criteriaBuilder.desc(orderExpression);
                }

                orderList.add(order);
            }
        }

        criteriaQuery = criteriaQuery.where(criteriaBuilder.and( // Устанавливаем условия where
                conditions.toArray(new Predicate[conditions.size()])
                )
        ).orderBy(orderList);

        return entityManager.createQuery(criteriaQuery).setHint(QueryHints.READ_ONLY, true).getResultList();
    }

}
