package dev.alnat.moneykeeper.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.alnat.moneykeeper.model.enums.UserOperation;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Группа, к которой принадлежит пользователь
 *
 * Содержит в себе набор прав, доступных этой группе
 *
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userGroupID", scope = UserGroup.class)
@Schema(description = "Группа пользователей")
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 7269453673167L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор группы", required = true, example = "1")
    private Integer userGroupID;

    @Column(nullable = false)
    @Schema(description = "Идентификатор группы", required = true, example = "user")
    private String key;

    @Column(nullable = false)
    @Schema(description = "Имя группы", required = true, example = "Пользователи")
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "UserGroup_UserOperation", joinColumns = @JoinColumn(name = "userGroupID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "userOperation", nullable = false)
    @Schema(description = "Список доступных операций для этой группы", required = true)
    private List<UserOperation> userOperationList;


    public UserGroup() {
        userOperationList = new ArrayList<>();
    }


    public Integer getUserGroupID() {
        return userGroupID;
    }

    public void setUserGroupID(Integer userGroupID) {
        this.userGroupID = userGroupID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserOperation> getUserOperationList() {
        return userOperationList;
    }

    public void setUserOperationList(List<UserOperation> userOperationList) {
        this.userOperationList = userOperationList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroup userGroup = (UserGroup) o;
        return Objects.equals(userGroupID, userGroup.userGroupID) &&
                Objects.equals(key, userGroup.key) &&
                Objects.equals(name, userGroup.name) &&
                Objects.equals(userOperationList, userGroup.userOperationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userGroupID, key, name, userOperationList);
    }

}
