package dev.alnat.moneykeeper.model;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.alnat.moneykeeper.model.enums.UserOperation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тест-кейсы конвертации модели UserGroup
 *
 * Created by @author AlNat on 22.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@JsonTest
public class UserGroupJSONTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JacksonTester<UserGroup> converter;


    @Test
    @DisplayName("Тест конвертации пустой сущности в JSON")
    public void testConvertToJSONEmptyAccount() throws Exception {
        UserGroup userGroup = new UserGroup();

        assertThat(this.converter.write(userGroup))
                .isStrictlyEqualToJson("/model/usergroup/empty_usergroup.json");
    }

    @Test
    @DisplayName("Тест конвертации сущности c ID в JSON")
    public void testConvertToJSONAccountWithID() throws Exception {
        UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupID(12345);

        assertThat(this.converter.write(userGroup))
                .isStrictlyEqualToJson("/model/usergroup/id_fill_usergroup.json");
    }

    @Test
    @DisplayName("Тест конвертации заполненной сущности без списка прав в JSON")
    public void testConvertToJSONFillWithoutPermissionUserGroup() throws Exception {
        UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupID(12345);
        userGroup.setKey("admin");
        userGroup.setName("Администраторы");
        userGroup.setUserOperationList(Collections.emptyList());

        assertThat(this.converter.write(userGroup))
                .isStrictlyEqualToJson("/model/usergroup/filled_without_operation_usergroup.json");
    }

    @Test
    @DisplayName("Тест конвертации заполненной сущности в JSON")
    public void testConvertToJSONFullFillUserGroup() throws Exception {
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

        assertThat(this.converter.write(userGroup))
                .isStrictlyEqualToJson("/model/usergroup/full_filled_usergroup.json");
    }

    @Test
    @DisplayName("Тест конвертации сущности с идентификатором из JSON")
    public void testConvertFromJSONUserGroupWithID() throws Exception {
        UserGroup userGroup = this.converter.read("/model/usergroup/id_fill_usergroup.json").getObject();
        assertEquals(12345, userGroup.getUserGroupID());
    }

    @Test
    @DisplayName("Тест конвертации сущности без списка прав из JSON")
    public void testConvertFromJSONUserGroupWithoutPermission() throws Exception {
        UserGroup userGroup = this.converter.read("/model/usergroup/filled_without_operation_usergroup.json").getObject();
        assertEquals(0, userGroup.getUserOperationList().size());
        assertEquals(12345, userGroup.getUserGroupID());
        assertEquals("admin", userGroup.getKey());
        assertEquals("Администраторы", userGroup.getName());
    }

    @Test
    @DisplayName("Тест конвертации сущности с неизвестным правом из JSON")
    public void testConvertFromJSONUserGroupWithUnknownPermission() {
        assertThrows(InvalidFormatException.class, () -> this.converter.read("/model/usergroup/unsupported_operation_filled_usergroup.json"));
    }

}
