package dev.alnat.moneykeeper.dao;

import dev.alnat.moneykeeper.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
