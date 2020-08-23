package dev.alnat.moneykeeper.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import dev.alnat.moneykeeper.model.enums.UserOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.*;

/**
 * Пользователь
 *
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userGroupID", scope = UserGroup.class)
@Schema(description = "Информация о конкретном пользователе")
public class User implements Serializable, UserDetails, CredentialsContainer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор пользователя", required = true, example = "1")
    private Integer userID;

    @Column(nullable = false)
    @Schema(description = "Пароль пользователя", required = true, example = "1")
    private String password;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[А-Яа-яЁёA-Za-z0-9 -]{0,255}$")
    @Schema(description = "Логин пользователя", required = true, example = "administrator")
    private String username;

    @Column(nullable = false)
    @Schema(description = "Флаг активности пользователя", required = true)
    private boolean enabled;

    @JacksonXmlElementWrapper(localName = "userGroupList")
    @JacksonXmlProperty(localName = "userGroup")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "User_UserGroup",
            joinColumns = {
                @JoinColumn(name = "userID", nullable = false, foreignKey = @ForeignKey(name = "usergroup_user_fkey"))
            },
            inverseJoinColumns = {
                @JoinColumn(name = "userGroupID", nullable = false, foreignKey = @ForeignKey(name = "userusergroup_usergroup_fkey"))
            })
    @Fetch(FetchMode.SELECT)
    @Schema(description = "Список групп, в которые входит пользователь", required = true)
    private List<UserGroup> userGroupList;


    public User() {
        this.userGroupList = new ArrayList<>();
    }


    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // У всех пользователей в системе должна быть глобальная роль для проверки авторизации в Security
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Конвертируем список операций в доступные права в Spring Security
        getUserOperationList().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.name())));

        return authorities;
    }

    /**
     * Формируем все операции доступные пользователю
     * @return Массив операций доступных пользователю
     */
    @JsonIgnore
    public Set<UserOperation> getUserOperationList() {
        Set<UserOperation> userOperationSet = new HashSet<>();
        userGroupList.forEach(i -> userOperationSet.addAll(i.getUserOperationList()));
        return userOperationSet;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Необходим, т.к. реализуем интерфейс UserDetails
    // Сейчас не используется
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    // Необходим, т.к. реализуем интерфейс UserDetails
    // Сейчас не используется
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    // Необходим, т.к. реализуем интерфейс UserDetails
    // Сейчас не используется
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(userID, user.userID) &&
                Objects.equals(password, user.password) &&
                Objects.equals(username, user.username) &&
                Objects.equals(userGroupList, user.userGroupList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, password, username, enabled, userGroupList);
    }

}

