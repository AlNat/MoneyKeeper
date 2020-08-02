package dev.alnat.moneykeeper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import dev.alnat.moneykeeper.model.Account;
import dev.alnat.moneykeeper.model.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

/**
 * Информация о счете в агрегированном виде
 * Содержит информацию об аккаунте, о всех его транзакция и о балансе по дням
 * А также ограничения выборки
 *
 * Created by @author AlNat on 26.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Информация о счете в агрегированном виде")
public class AccountInfo {

    @Schema(description = "Информация о самом счете")
    private Account account;

    @Schema(description = "Данные о балансе счета по дню")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "dayBalance")
    private List<AccountBalance> balance;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Schema(description = "Дата начала выборки", required = true, example = "2020-01-01", pattern = "yyyy-MM-dd")
    private LocalDate from;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Schema(description = "Дата завершения выборки", required = true, example = "2020-01-10", pattern = "yyyy-MM-dd")
    private LocalDate to;

    @JacksonXmlElementWrapper(localName = "transactions")
    @JacksonXmlProperty(localName = "transaction")
    @Schema(description = "Список транзакций")
    private List<Transaction> transactionList;


    private AccountInfo(Builder builder) {
        account = builder.account;
        balance = builder.balance;
        from = builder.from;
        to = builder.to;
        transactionList = builder.transactionList;
    }

    public AccountInfo() {
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<AccountBalance> getBalance() {
        return balance;
    }

    public void setBalance(List<AccountBalance> balance) {
        this.balance = balance;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private Account account;
        private List<AccountBalance> balance;
        private LocalDate from;
        private LocalDate to;
        private List<Transaction> transactionList;

        private Builder() {
        }

        public Builder fromAccount(Account val) {
            account = val;
            return this;
        }

        public Builder balance(List<AccountBalance> val) {
            balance = val;
            return this;
        }

        public Builder from(LocalDate val) {
            from = val;
            return this;
        }

        public Builder to(LocalDate val) {
            to = val;
            return this;
        }

        public Builder withTransactionList(List<Transaction> val) {
            transactionList = val;
            return this;
        }

        public AccountInfo build() {
            return new AccountInfo(this);
        }
    }

}
