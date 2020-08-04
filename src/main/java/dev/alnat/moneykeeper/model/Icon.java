package dev.alnat.moneykeeper.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.alnat.moneykeeper.model.abstracts.CreatedUpdated;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * Иконка на категорию
 * Используется для предпросмотра и отображения
 *
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Entity
@Table(name = "icon")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "iconID", scope = Icon.class)
@Schema(description = "Иконка категория покупки")
public class Icon extends CreatedUpdated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer iconID;

    @Column(nullable = false)
    @NotNull
    public byte[] data;


    public Icon() {
    }


    public Integer getIconID() {
        return iconID;
    }

    public void setIconID(Integer iconID) {
        this.iconID = iconID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Icon icon = (Icon) o;
        return Objects.equals(iconID, icon.iconID) &&
                Arrays.equals(data, icon.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(iconID);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

}
