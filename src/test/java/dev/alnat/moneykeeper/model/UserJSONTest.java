package dev.alnat.moneykeeper.model;

import dev.alnat.moneykeeper.model.enums.UserOperation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест-кейсы конвертации модели User
 *
 * Created by @author AlNat on 22.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@JsonTest
public class UserJSONTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JacksonTester<User> converter;


    @Test
    @DisplayName("Тест конвертации пустой сущности в JSON")
    public void testConvertToJSONEmptyUser() throws Exception {
        User user = new User();

        assertThat(this.converter.write(user))
                .isStrictlyEqualToJson("/model/user/empty_user.json");
    }

    @Test
    @DisplayName("Тест конвертации сущности c ID в JSON")
    public void testConvertToJSONUserWithID() throws Exception {
        User user = new User();
        user.setUserID(12345);

        assertThat(this.converter.write(user))
                .isStrictlyEqualToJson("/model/user/id_fill_user.json");
    }

    @Test
    @DisplayName("Тест конвертации заполненной сущности в JSON")
    public void testConvertToJSONFullFillUser() throws Exception {
        User user = new User();
        user.setUserID(12345);
        user.setUsername("admin");
        user.setEnabled(true);

        UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupID(12345);
        userGroup.setKey("admin");
        userGroup.setName("Администраторы");
        userGroup.setUserOperationList(List.of(
                UserOperation.USER_GROUP_CREATE,
                UserOperation.USER_GROUP_CHANGE,
                UserOperation.USER_GROUP_DELETE,
                UserOperation.USER_GROUP,
                UserOperation.USER_GROUP_LIST
        ));
        user.setUserGroupList(Collections.singletonList(userGroup));

        assertThat(this.converter.write(user))
                .isStrictlyEqualToJson("/model/user/full_filled_user.json");
    }


    @Test
    @DisplayName("Тест конвертации сущности с идентификатором из JSON")
    public void testConvertFromJSONUserWithID() throws Exception {
        User user = this.converter.read("/model/user/id_fill_user.json").getObject();
        assertEquals(12345, user.getUserID());
    }

    @Test
    @DisplayName("Тест конвертации полностью заполненной сущности из JSON")
    public void testConvertFromJSONUserFull() throws Exception {
        User user = this.converter.read("/model/user/full_filled_user.json").getObject();
        assertEquals(12345, user.getUserID());
        assertEquals("admin", user.getUsername());
        assertEquals(Set.of(
                UserOperation.USER_GROUP_CREATE,
                UserOperation.USER_GROUP_CHANGE,
                UserOperation.USER_GROUP_DELETE,
                UserOperation.USER_GROUP,
                UserOperation.USER_GROUP_LIST
        ), user.getUserOperationList());
    }

}
