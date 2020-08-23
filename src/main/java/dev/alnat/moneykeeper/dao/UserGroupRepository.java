package dev.alnat.moneykeeper.dao;

import dev.alnat.moneykeeper.model.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, Integer> {

    Optional<UserGroup> findByKey(String key);

}
