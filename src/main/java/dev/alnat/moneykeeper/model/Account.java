package dev.alnat.moneykeeper.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import dev.alnat.moneykeeper.model.abstracts.CreatedUpdated;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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

    @JacksonXmlElementWrapper(localName = "accountList")
    @JacksonXmlProperty(localName = "account")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<Transaction> transactionList;


}
