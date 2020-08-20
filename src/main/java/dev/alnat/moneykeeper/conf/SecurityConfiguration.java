package dev.alnat.moneykeeper.conf;

import dev.alnat.moneykeeper.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) // Включаем конфигурирование аннотациями
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringAntMatchers("/api/**").and() // Под все API запросы исключаем CSRF
                .httpBasic().and() // На любой запрос - нужна авторизация, она - Basic
                .authorizeRequests().anyRequest().authenticated().and() // Любой запрос должен быть авторизован
                .sessionManagement()
                    .maximumSessions(1).and()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .userDetailsService(userService); // Сервис, который отвечает за получение пользователей

        // TODO После UI добавить отдельную авторизацию
        //  Также сразу добавить AuthenticationSuccessHandler
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}
