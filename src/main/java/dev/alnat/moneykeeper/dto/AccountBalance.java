package dev.alnat.moneykeeper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO для хранения баланса счета на дату
 * Правила выборки и сами запросы расположены в
 * @see dev.alnat.moneykeeper.model.Account
 *
 * Created by @author AlNat on 02.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Schema(description = "Информация о балансе счета по дню")
public class AccountBalance {

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Schema(description = "Дата баланса", example = "2020-01-01", pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Schema(description = "Баланс")
    private BigDecimal balance;


    public AccountBalance() {
    }

    public AccountBalance(LocalDate day, BigDecimal balance) {
        this.date = day;
        this.balance = balance;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBalance that = (AccountBalance) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, balance);
    }

}
