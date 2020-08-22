package dev.alnat.moneykeeper.controller;

import dev.alnat.moneykeeper.conf.SecurityConfiguration;
import dev.alnat.moneykeeper.controller.api.AccountController;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.service.AccountService;
import dev.alnat.moneykeeper.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Набор тест-кейсов на проверку корректности работы Security в контроллерах
 *
 * Для тестирования взят AccountController
 *
 * Created by @author AlNat on 22.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@WebMvcTest(AccountController.class)
@Import(SecurityConfiguration.class) // Подключаем конфигурацию Security
public class SecurityControllerTest {

    @MockBean
    private AccountService service;

    // Мокаем бин для обеспечения доступа к Security
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT_LIST")
    @DisplayName("Тестирование обращение к endpoint без прав на эту операцию")
    void testGetAllAccountWitPermission() throws Exception {
        doReturn(Collections.emptyList())
                .when(service).getAccountList();

        mockMvc.perform(get("/api/account/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser(username = "test")
    @DisplayName("Тестирование обращение к endpoint без прав на эту операцию")
    void testGetAllAccountWithoutPermission() throws Exception {
        doReturn(Collections.emptyList())
                .when(service).getAccountList();

        mockMvc.perform(get("/api/account/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT_CREATE")
    @DisplayName("Тестирование обращение к endpoint с POST для проверки отключения проверки CSRF")
    void testCreateNewAccount() throws Exception {
        String json = "{\n" +
                "  \"accountID\": 42,\n" +
                "  \"key\": \"card\",\n" +
                "  \"name\": \"Тестовый аккаунт\",\n" +
                "  \"type\": \"CARD\"\n" +
                "}";

        doNothing()
                .when(service).create(Mockito.any(Account.class));

        mockMvc.perform(post("/api/account/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "TEST")
    @DisplayName("Тестирование обращение к endpoint для удаления без прав на операцию")
    void testDeleteAccountWithoutPermission() throws Exception {
        doNothing()
                .when(service)
                .delete(anyInt());

        mockMvc.perform(delete("/api/account/42"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Тестирование обращения к endpoint без любой авторизации")
    void testDeleteAccountWithoutAuthorized() throws Exception {
        doNothing()
                .when(service)
                .delete(anyInt());

        mockMvc.perform(delete("/api/account/42"))
                .andExpect(status().isUnauthorized());
    }

}
