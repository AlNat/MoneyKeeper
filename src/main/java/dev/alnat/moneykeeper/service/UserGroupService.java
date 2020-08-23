package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.model.UserGroup;
import dev.alnat.moneykeeper.model.enums.UserOperation;

import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface UserGroupService {

    Optional<UserGroup> get(Integer userGroupID);

    Optional<UserGroup> get(String key);

    List<UserGroup> getUserList();

    void create(UserGroup userGroup);

    void update(UserGroup userGroup);

    void delete(Integer userGroupID);

    void delete(UserGroup userGroup);


    /**
     * Добавление операции к группе
     *
     * @param userGroupID идентификатор группы
     * @param operation операция
     */
    void addPermission(Integer userGroupID, UserOperation operation) throws MoneyKeeperException;

    /**
     * Добавление операции к группе
     *
     * @param userGroupKey идентификатор группы
     * @param operation операция
     */
    void addPermission(String userGroupKey, UserOperation operation) throws MoneyKeeperException;

    /**
     * Добавление операции к группе
     *
     * @param userGroup группа
     * @param operation операция
     */
    void addPermission(UserGroup userGroup, UserOperation operation) throws MoneyKeeperException;


    /**
     * Удаление операции у группы
     *
     * @param userGroupID идентификатор группы
     * @param operation операция
     */
    void removePermission(Integer userGroupID, UserOperation operation) throws MoneyKeeperException;

    /**
     * Удаление операции у группы
     *
     * @param userGroupKey идентификатор группы
     * @param operation операция
     */
    void removePermission(String userGroupKey, UserOperation operation) throws MoneyKeeperException;

    /**
     * Удаление операции у группы
     *
     * @param userGroup группа
     * @param operation операция
     */
    void removePermission(UserGroup userGroup, UserOperation operation) throws MoneyKeeperException;

}
