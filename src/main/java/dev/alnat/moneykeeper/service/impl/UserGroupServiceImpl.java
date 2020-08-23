package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.UserGroupRepository;
import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.exception.MoneyKeeperIllegalArgumentException;
import dev.alnat.moneykeeper.exception.MoneyKeeperNotFoundException;
import dev.alnat.moneykeeper.model.UserGroup;
import dev.alnat.moneykeeper.model.enums.UserOperation;
import dev.alnat.moneykeeper.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserGroupRepository userGroupRepository;

    @Autowired
    public UserGroupServiceImpl(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }


    @Override
    public Optional<UserGroup> get(Integer userGroupID) {
        return userGroupRepository.findById(userGroupID);
    }

    @Override
    public Optional<UserGroup> get(String key) {
        return userGroupRepository.findByKey(key);
    }

    @Override
    public List<UserGroup> getUserGroupList() {
        return StreamSupport
                .stream(userGroupRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void create(UserGroup userGroup) {
        userGroupRepository.save(userGroup);
    }

    @Override
    public void update(UserGroup userGroup) {
        userGroupRepository.save(userGroup);
    }

    @Override
    public void delete(Integer userGroupID) {
        userGroupRepository.deleteById(userGroupID);
    }

    @Override
    public void delete(UserGroup userGroup) {
        userGroupRepository.delete(userGroup);
    }

    @Override
    public void addPermission(Integer userGroupID, UserOperation operation) throws MoneyKeeperException {
        Optional<UserGroup> g = get(userGroupID);
        if (g.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найдена группа с идентификатором " + userGroupID + "!");
        }

        addPermission(g.get(), operation);
    }

    @Override
    public void addPermission(String userGroupKey, UserOperation operation) throws MoneyKeeperException {
        Optional<UserGroup> g = get(userGroupKey);
        if (g.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найдена группа с идентификатором " + userGroupKey + "!");
        }

        addPermission(g.get(), operation);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true) // Сбрасываем кеш при изменениях прав у группы
    public void addPermission(UserGroup userGroup, UserOperation operation) throws MoneyKeeperException {
        // Если такая операция уже есть - ошибка!
        if (userGroup.getUserOperationList().contains(operation)) {
            log.error("Ошибка добавления операции к группе - операция {} уже есть у группы {}", operation.name(), userGroup.getKey());
            throw new MoneyKeeperIllegalArgumentException(
                    String.format("Ошибка добавления операции к группе - операция %s уже есть у группы %s", operation.name(), userGroup.getKey())
            );
        }

        userGroup.getUserOperationList().add(operation);
        update(userGroup);
    }

    @Override
    public void removePermission(Integer userGroupID, UserOperation operation) throws MoneyKeeperException {
        Optional<UserGroup> g = get(userGroupID);
        if (g.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найдена группа с идентификатором " + userGroupID + "!");
        }

        removePermission(g.get(), operation);
    }

    @Override
    public void removePermission(String userGroupKey, UserOperation operation) throws MoneyKeeperException {
        Optional<UserGroup> g = get(userGroupKey);
        if (g.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найдена группа с идентификатором " + userGroupKey + "!");
        }

        removePermission(g.get(), operation);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true) // Сбрасываем кеш при изменениях прав у группы
    public void removePermission(UserGroup userGroup, UserOperation operation) throws MoneyKeeperException {
        // Если такая операция уже есть - ошибка!
        if (!userGroup.getUserOperationList().contains(operation)) {
            log.error("Ошибка добавления операции к группе - операция {} отсутствует у группы {}", operation.name(), userGroup.getKey());
            throw new MoneyKeeperIllegalArgumentException(
                    String.format("Ошибка добавления операции к группе - операция %s отсутствует у группы %s", operation.name(), userGroup.getKey())
            );
        }

        userGroup.getUserOperationList().remove(operation);
        update(userGroup);
    }

}
