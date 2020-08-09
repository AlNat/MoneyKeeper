package dev.alnat.moneykeeper.service.impl;

import dev.alnat.moneykeeper.dao.IconRepository;
import dev.alnat.moneykeeper.model.Icon;
import dev.alnat.moneykeeper.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by @author AlNat on 02.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
public class IconServiceImpl implements IconService {

    @Autowired
    private IconRepository iconRepository;

    @Override
    public Optional<Icon> get(Integer iconID) {
        return iconRepository.findById(iconID);
    }

    @Override
    public List<Icon> getAllIcon() {
        return StreamSupport
                .stream(iconRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void create(Icon icon) {
        iconRepository.save(icon);
    }

    @Override
    public void create(byte[] icon) {
        Icon i = new Icon();
        i.setData(icon);

        create(i);
    }

    @Override
    public void update(Icon icon) {
        iconRepository.save(icon);
    }

    @Override
    public void delete(Icon icon) {
        iconRepository.delete(icon);
    }

    @Override
    public void delete(Integer iconID) {
        iconRepository.deleteById(iconID);
    }

}
