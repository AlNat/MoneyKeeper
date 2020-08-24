package dev.alnat.moneykeeper.conf;

import dev.alnat.moneykeeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
public class SecurityConfiguration {


    /**
     * Конфигурация для API
     * Аналог http блока в XML
     */
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserService userService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .authorizeRequests().anyRequest().authenticated().and() // Любой запрос должен быть авторизован
                    .httpBasic().and() // На любой запрос - нужна авторизация, она - Basic
                    .csrf().ignoringAntMatchers("/api/**").and() // Под все API запросы исключаем CSRF
                    .userDetailsService(userService); // Сервис, который отвечает за получение пользователей
        }
    }


    /**
     * Конфигурация для всех остальных endpoint
     */
    @Configuration
    public static class WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserService userService;

        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler() {
            return new CustomAuthenticationSuccessHandler();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/resources/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests().antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll().and() // Открываем Swagger
                    .sessionManagement() // Всегда делаем сессию из 1 учетки
                        .maximumSessions(1).and()
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                    .csrf().and() // Под все запросы CSRF
                    .authorizeRequests().anyRequest().authenticated().and() // Любой запрос должен быть авторизован
                    .userDetailsService(userService); // Сервис, который отвечает за получение пользователей

            // TODO Login Page
            // TODO Logout page
        }
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Для упрощения кеширования - не стираем данные пользователя
    // Это безаопсано, т.к. мы все равно все храним в BCrypt-е
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false);
    }

}
