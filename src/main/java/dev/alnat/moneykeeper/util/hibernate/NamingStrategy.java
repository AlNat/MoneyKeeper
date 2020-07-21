package dev.alnat.moneykeeper.util.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

import java.io.Serializable;

/**
 * Необходимо для смены именование генерируемых сущностей из file_name в filename
 * Здесь можно описать любой стандарт, но в данном проекте устоялся именно такой формат
 *
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
public class NamingStrategy extends SpringPhysicalNamingStrategy implements Serializable {

    public static final PhysicalNamingStrategyStandardImpl INSTANCE = new PhysicalNamingStrategyStandardImpl();

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return new Identifier(name.getText().toLowerCase(), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return new Identifier(name.getText().toLowerCase(), name.isQuoted());
    }

}