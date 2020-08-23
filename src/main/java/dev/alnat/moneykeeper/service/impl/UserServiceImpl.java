package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.UserRepository;
import dev.alnat.moneykeeper.exception.MoneyKeeperException;
import dev.alnat.moneykeeper.exception.MoneyKeeperNotFoundException;
import dev.alnat.moneykeeper.model.User;
import dev.alnat.moneykeeper.model.UserGroup;
import dev.alnat.moneykeeper.service.UserGroupService;
import dev.alnat.moneykeeper.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    private final UserGroupService userGroupService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserGroupService userGroupService) {
        this.userRepository = userRepository;
        this.userGroupService = userGroupService;
    }

    @Override
    public Optional<User> get(Integer userID) {
        return userRepository.findById(userID);
    }

    @Override
    public Optional<User> get(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public List<User> getUserList() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Integer userID) {
        userRepository.deleteById(userID);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void addToGroup(Integer userID, Integer userGroupID) throws MoneyKeeperException {
        Optional<User> g = get(userID);
        if (g.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найден пользователь с идентификатором " + userID + "!");
        }

        Optional<UserGroup> ug = userGroupService.get(userGroupID);
        if (ug.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найдена группа с идентификатором " + userGroupID + "!");
        }

        addToGroup(g.get(), ug.get());
    }

    @Override
    public void addToGroup(String userName, String userGroupKey) throws MoneyKeeperException {
        Optional<User> g = get(userName);
        if (g.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найден пользователь с идентификатором " + userName + "!");
        }

        Optional<UserGroup> ug = userGroupService.get(userGroupKey);
        if (ug.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найдена группа с идентификатором " + userGroupKey + "!");
        }

        addToGroup(g.get(), ug.get());
    }

    @Override
    public void addToGroup(User user, UserGroup userGroup) throws MoneyKeeperException {
        // Если пользователь уже входит в эту группу - ошибка!
        if (user.getUserGroupList().contains(userGroup)) {
            log.error("Ошибка добавления пользователя {} к группе {} - он уже есть в ней!", user.getUsername(), userGroup.getKey());
            throw new MoneyKeeperException(
                    String.format("Ошибка добавления пользователя %s к группе %s - он уже есть в ней!", user.getUsername(), userGroup.getKey())
            );
        }

        user.getUserGroupList().remove(userGroup);
        update(user);
    }

    @Override
    public void removeFromGroup(Integer userID, Integer userGroupID) throws MoneyKeeperException {
        Optional<User> g = get(userID);
        if (g.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найден пользователь с идентификатором " + userID + "!");
        }

        Optional<UserGroup> ug = userGroupService.get(userGroupID);
        if (ug.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найдена группа с идентификатором " + userGroupID + "!");
        }

        removeFromGroup(g.get(), ug.get());
    }

    @Override
    public void removeFromGroup(String userName, String userGroupKey) throws MoneyKeeperException {
        Optional<User> g = get(userName);
        if (g.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найден пользователь с идентификатором " + userName + "!");
        }

        Optional<UserGroup> ug = userGroupService.get(userGroupKey);
        if (ug.isEmpty()) {
            throw new MoneyKeeperNotFoundException("Не найдена группа с идентификатором " + userGroupKey + "!");
        }

        removeFromGroup(g.get(), ug.get());
    }

    @Override
    public void removeFromGroup(User user, UserGroup userGroup) throws MoneyKeeperException {
        // Если пользователь не входит в эту группу - ошибка!
        if (!user.getUserGroupList().contains(userGroup)) {
            log.error("Ошибка удаления пользователя {} из группы {} - он в ней не состоит!", user.getUsername(), userGroup.getKey());
            throw new MoneyKeeperException(
                    String.format("Ошибка удаления пользователя %s из группы %s - он в ней не состоит!", user.getUsername(), userGroup.getKey())
            );
        }

        user.getUserGroupList().add(userGroup);
        update(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> u = userRepository.findByUsername(s);

        if (u.isEmpty()) throw new UsernameNotFoundException("Не найден пользователь с именем " + s);

        return u.get();
    }

}
