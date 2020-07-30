package dev.alnat.moneykeeper.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import dev.alnat.moneykeeper.model.abstracts.CreatedUpdated;
import dev.alnat.moneykeeper.model.enums.AccountTypeEnum;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by @author AlNat on 21.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Entity
@Table(name = "account")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountID", scope = Account.class)
public class Account extends CreatedUpdated implements Serializable {

    private static final long serialVersionUID = 251152522L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountID;

    @Column(nullable = false, unique = true)
    @NotNull
    private String name;

    @Column
    private String description;

    @Formula("(SELECT COALESCE(sum(t.amount), 0) FROM transaction as t WHERE t.status = 0 AND t.accountID = accountID)")
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AccountTypeEnum type;

    @JacksonXmlElementWrapper(localName = "accountList")
    @JacksonXmlProperty(localName = "account")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
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
                Objects.equals(name, account.name) &&
                Objects.equals(description, account.description) &&
                Objects.equals(balance, account.balance) &&
                type == account.type &&
                Objects.equals(transactionList, account.transactionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, name, description, balance, type, transactionList);
    }

}
