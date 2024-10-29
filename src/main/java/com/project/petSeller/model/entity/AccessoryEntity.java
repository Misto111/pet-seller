package com.project.petSeller.model.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "accessories")
public class AccessoryEntity extends BaseEntity{

    @NotNull
    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;

    @NotEmpty
    private String description;
    @NotNull
    @ManyToOne
    private AccessoryModelEntity accessoryModel;

    @NotEmpty
    private String imageUrl;
    @NotNull
    private BigDecimal price;

    public UUID getUuid() {
        return uuid;
    }

    public AccessoryEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AccessoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public AccessoryModelEntity getAccessoryModel() {
        return accessoryModel;
    }

    public AccessoryEntity setAccessoryModel(AccessoryModelEntity accessoryModel) {
        this.accessoryModel = accessoryModel;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AccessoryEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AccessoryEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
