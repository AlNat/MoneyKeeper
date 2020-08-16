package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.UserRepository;
import dev.alnat.moneykeeper.model.User;
import dev.alnat.moneykeeper.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> u = userRepository.findByUsername(s);

        if (u.isEmpty()) throw new UsernameNotFoundException("Не найден пользователь с именем " + s);

        User user = u.get();
        Hibernate.initialize(user.getUserGroupList());
        return user;
    }

}
