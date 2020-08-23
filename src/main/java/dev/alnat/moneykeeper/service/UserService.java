package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.model.User;
import dev.alnat.moneykeeper.model.UserGroup;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface UserService extends UserDetailsService {

    Optional<User> get(Integer userID);

    Optional<User> get(String userName);

    List<User> getUserList();

    void create(User user);

    void update(User user);

    void delete(Integer userID);

    void delete(User user);


    /**
     * Метод добавления пользователя в группу
     *
     * @param userID идентификатор пользователя
     * @param userGroupID идентификатор группы
     */
    void addToGroup(Integer userID, Integer userGroupID) throws MoneyKeeperException;

    /**
     * Метод добавления пользователя в группу
     *
     * @param userName имя пользователя
     * @param userGroupKey идентификатор группы
     */
    void addToGroup(String userName, String userGroupKey) throws MoneyKeeperException;

    /**
     * Метод добавления пользователя в группу
     *
     * @param user пользователь
     * @param userGroup группа
     */
    void addToGroup(User user, UserGroup userGroup) throws MoneyKeeperException;


    /**
     * Метод удаления пользователя из группы
     *
     * @param userID идентификатор пользователя
     * @param userGroupID идентификатор группы
     */
    void removeFromGroup(Integer userID, Integer userGroupID) throws MoneyKeeperException;

    /**
     * Метод удаления пользователя из группы
     *
     * @param userName имя пользователя
     * @param userGroupKey идентификатор группы
     */
    void removeFromGroup(String userName, String userGroupKey) throws MoneyKeeperException;

    /**
     * Метод удаления пользователя из группы
     *
     * @param user пользователь
     * @param userGroup группа
     */
    void removeFromGroup(User user, UserGroup userGroup) throws MoneyKeeperException;

}
