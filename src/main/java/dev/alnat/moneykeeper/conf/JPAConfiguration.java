package dev.alnat.moneykeeper.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@EnableJpaRepositories(basePackages = "dev.alnat.moneykeeper.dao")
@Configuration
public class JPAConfiguration {

}
