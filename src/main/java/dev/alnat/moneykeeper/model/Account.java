package dev.alnat.moneykeeper.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import dev.alnat.moneykeeper.dto.AccountBalance;
import dev.alnat.moneykeeper.model.abstracts.CreatedUpdated;
import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by @author AlNat on 21.07.2020.
 * Licensed by Apache License, Version 2.0
 */
// Ниже расположена информация о работать с классом AccountBalance
// Это должно быть расположено над Entity, а делать его Entity нет смысла
// Поэтому расположен тут - это информация по счету

// Описываем как мапить результат запроса (ResultSet)
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "AccountBalance",
                classes = {
                        @ConstructorResult(
                                targetClass = AccountBalance.class,
                                columns = {
                                        @ColumnResult(name = "day", type = LocalDate.class),
                                        @ColumnResult(name = "balance")
                                }
                        )
                }
        )
})
// Описываем запросы и именуем их - дальше в SpringData сможем их использовать
@NamedNativeQuery(name = "getDataFromAccountBalance",
        resultClass = AccountBalance.class,
        resultSetMapping ="AccountBalance",
        query = "SELECT * FROM account_balance(:accountID)")
@NamedNativeQuery(name = "getDataFromAccountBalanceWithDate",
        resultClass = AccountBalance.class,
        resultSetMapping ="AccountBalance",
        query = "SELECT * FROM account_balance(:accountID, :from, :to)")

// Информация по самой Entity
@Entity
@Table(name = "account")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountID", scope = Account.class)
@Schema(description = "Описание счета")
public class Account extends CreatedUpdated implements Serializable {

    private static final long serialVersionUID = 251152522L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор счета в БД")
    private Integer accountID;

    @Column(nullable = false, unique = true)
    @NotNull
    @Schema(description = "Идентификатор счета", required = true)
    private String key;

    @Column(nullable = false)
    @NotNull
    @Schema(description = "Имя счета", required = true)
    private String name;

    @Column
    @Schema(description = "Описание", required = false)
    private String description;

    @Formula("(SELECT COALESCE(sum(t.amount), 0) FROM transaction as t WHERE t.status = 0 AND t.accountID = accountID)")
    @Schema(description = "Текущий баланс счета")
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @Schema(description = "Тип счета", required = true)
    private AccountTypeEnum type;

    @JacksonXmlElementWrapper(localName = "accountList")
    @JacksonXmlProperty(localName = "account")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @Schema(description = "Список проводок по счету", required = false)
    private List<Transaction> transactionList;


    public Account() {
        this.transactionList = new ArrayList<>();
    }


    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountTypeEnum getType() {
        return type;
    }

    public void setType(AccountTypeEnum type) {
        this.type = type;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountID, account.accountID) &&
                Objects.equals(key, account.key) &&
                Objects.equals(name, account.name) &&
                Objects.equals(description, account.description) &&
                Objects.equals(balance, account.balance) &&
                type == account.type &&
                Objects.equals(transactionList, account.transactionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, key, name, description, balance, type, transactionList);
    }

}
