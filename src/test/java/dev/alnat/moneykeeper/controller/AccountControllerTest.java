package dev.alnat.moneykeeper.controller;

import dev.alnat.moneykeeper.controller.api.AccountController;
import dev.alnat.moneykeeper.dto.AccountInfo;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;
import dev.alnat.moneykeeper.service.AccountService;
import dev.alnat.moneykeeper.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Набор тестов для тестирования AccountController-а
 * Причем это скорее интеграционные тесты
 *
 * Конвертация и детали самих сущностей тестируется в
 * @see dev.alnat.moneykeeper.model.AccountJSONTest
 *
 * Общий процесс тестирования построен через BDD,
 * но иногда в классическом стиле
 *
 * Created by @author AlNat on 05.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @MockBean
    private AccountService service;

    // Мокаем бин для обеспечения доступа к Security
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    private Account testAccount;

    private String testAccountInJSON;


    @BeforeEach
    void init() {
        testAccount = new Account();
        testAccount.setAccountID(42);
        testAccount.setKey("card");
        testAccount.setName("Тестовый аккаунт");
        testAccount.setType(AccountTypeEnum.CARD);

        testAccountInJSON = "{\n" +
                "  \"accountID\": 42,\n" +
                "  \"key\": \"card\",\n" +
                "  \"name\": \"Тестовый аккаунт\",\n" +
                "  \"type\": \"CARD\"\n" +
                "}";
    }

    /**
     * Тест-кейс
     * <p>
     * Счет получается по его ID и возвращается в теле ответа с кодом 200
     */
    @Test
    @WithMockUser(authorities = "ACCOUNT")
    @DisplayName("Тестирование успешного получения существующей сущности по ID")
    void testGetAccountWithID() throws Exception {
        given(service.get(42))
                .willReturn(Optional.of(testAccount));

        mockMvc.perform(get("/api/account/42")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(testAccountInJSON));
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT")
    @DisplayName("Тестирование получения несуществующей сущности по ID")
    void testNotFoundAccountWithID() throws Exception {
        given(service.get(any()))
                .willReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/account/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT_LIST")
    @DisplayName("Тестирование получения несуществующей сущности по ID")
    void testGetAllAccount() throws Exception {
        given(service.getAccountList())
                .willReturn(Collections.singletonList(testAccount));

        mockMvc.perform(get("/api/account/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT")
    @DisplayName("Тестирование получения сущности по идентификатору")
    void testSearchAccount() throws Exception {
        given(service.getAccountByKey("card"))
                .willReturn(Optional.of(testAccount));

        mockMvc.perform(get("/api/account/search?accountKey=card")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT_CREATE")
    @DisplayName("Тестирование создание новой сущности переданной в теле запроса в JSON")
    void testCreateAccount() throws Exception {
        doNothing()
                .when(service)
                .create(any());

        mockMvc.perform(
                post("/api/account/")
                .content(testAccountInJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());
    }


    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT_CREATE")
    @DisplayName("Тестирование создание новой сущности переданной в теле запроса в фо XML")
    void testCreateAccountXML() throws Exception {
        String testAccountInXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<Account>\n" +
                "  <accountID>12345</accountID>\n" +
                "</Account>";

        doNothing()
                .when(service)
                .create(any(Account.class));

        mockMvc.perform(
                post("/api/account/")
                        .content(testAccountInXML)
                        .contentType(MediaType.APPLICATION_XML)
        )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT_CREATE")
    @DisplayName("Тестирование создание новой сущности переданной через параметры")
    void testCreateAccountByParams() throws Exception {
        doNothing()
                .when(service)
                .create(anyString(), anyString(), anyString(), any(AccountTypeEnum.class));

        mockMvc.perform(
                post("/api/account/custom")
                        .queryParam("name", "test")
                        .queryParam("type", "CARD")
                        .queryParam("key", "test")
        )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT_CREATE")
    @DisplayName("Тестирование ошибки создание новой сущности переданной через параметры")
    void testErrorCreateAccountByParams() throws Exception {
        mockMvc.perform(
                post("/api/account/custom")
                        .queryParam("type", "CARD")
                        .queryParam("key", "test")
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT_DELETE")
    @DisplayName("Тестирование удаления сущности")
    void testDeleteAccount() throws Exception {
        doNothing()
                .when(service)
                .delete(anyInt());

        mockMvc.perform(delete("/api/account/42"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", authorities = "ACCOUNT")
    @DisplayName("Тестирование получения агрегированной информации по счету")
    void testGetAccountInfo() throws Exception {
        given(service.getAccountInfo(anyString(), any(), any()))
                .willReturn(new AccountInfo());

        mockMvc.perform(get("/api/account/info")
                .queryParam("accountKey", "key")
                .queryParam("from", "2020-01-01")
                .queryParam("to", "2020-01-02")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
