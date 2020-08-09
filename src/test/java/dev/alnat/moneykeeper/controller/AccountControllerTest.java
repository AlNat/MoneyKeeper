package dev.alnat.moneykeeper.controller;

import dev.alnat.moneykeeper.controller.api.AccountController;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Набор тестов для тестирования AccountController-а
 * Причем это скорее интеграционный тест
 *
 * Created by @author AlNat on 05.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @MockBean
    private AccountService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Тестирование успешного получения существующей сущности по ID")
    void testGetAccountWithID() throws Exception {
        Account testAccount = new Account();
        testAccount.setAccountID(12345);

        given(service.get(any()))
                .willReturn(java.util.Optional.of(testAccount));

        mockMvc.perform(get("/api/account/42")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
