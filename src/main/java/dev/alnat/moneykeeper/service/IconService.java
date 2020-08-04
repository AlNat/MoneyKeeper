package dev.alnat.moneykeeper.service;

import dev.alnat.moneykeeper.model.Icon;

import java.util.List;
import java.util.Optional;

/**
 * Created by @author AlNat on 02.08.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface IconService {

    Optional<Icon> get(Integer iconID);

    List<Icon> getAllIcon();

    void create(Icon icon);

    void create(byte[] icon);

    void update(Icon icon);

    void delete(Icon icon);

    void delete(Integer iconID);

}
