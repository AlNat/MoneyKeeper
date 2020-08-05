package dev.alnat.moneykeeper.model;

import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест-кейсы конвертации модели Account в JSON и обратно
 *
 * Created by @author AlNat on 05.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@JsonTest
public class AccountJSONTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JacksonTester<Account> converter;


    @Test
    @DisplayName("Тест конвертации пустой сущности в JSON")
    public void testConvertToJSONEmptyAccount() throws Exception {
        Account account = new Account();

        assertThat(this.converter.write(account))
                .isStrictlyEqualToJson("/model/empty_account.json");
    }

    @Test
    @DisplayName("Тест конвертации сущности c ID в JSON")
    public void testConvertToJSONAccountWithID() throws Exception {
        Account account = new Account();
        account.setAccountID(12345);

        assertThat(this.converter.write(account))
                .isStrictlyEqualToJson("/model/id_fill_account.json");
    }

    @Test
    @DisplayName("Тест конвертации заполненной сущности в JSON")
    public void testConvertToJSONFullFillAccount() throws Exception {
        LocalDateTime time = LocalDateTime.of(2020, 10, 10, 12, 12, 12);

        Account account = new Account();
        account.setKey("test_key");
        account.setAccountID(123);
        account.setType(AccountTypeEnum.CARD);
        account.setBalance(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
        account.setCreated(time);
        account.setUpdated(time);

        assertThat(this.converter.write(account))
                .isStrictlyEqualToJson("/model/full_fill_account.json");
    }


    @Test
    @DisplayName("Тест конвертации сущности с идентификатором из JSON")
    public void testConvertFromJSONAccountWithID() throws Exception {
        Account account = this.converter.read("/model/id_fill_account.json").getObject();
        assertEquals(12345, account.getAccountID());
    }

}
