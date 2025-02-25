package com.project.petSeller.model.entity;

import com.project.petSeller.model.enums.ColorEnum;
import com.project.petSeller.model.enums.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "offers")
public class PetOfferEntity extends BaseEntity {

    @NotNull
    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;

    @NotEmpty
    private String description;

    @NotNull
    @ManyToOne
    private BreedEntity breed;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @ManyToOne
    private UserEntity seller;

    @NotEmpty
    private String imageUrl;

    @Positive
    private Double weight;

    @NotNull
    private BigDecimal price;

    @Min(1)
    private int years;


    public UUID getUuid() {
        return uuid;
    }

    public PetOfferEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PetOfferEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public BreedEntity getBreed() {
        return breed;
    }

    public PetOfferEntity setBreed(BreedEntity breed) {
        this.breed = breed;
        return this;
    }

    public ColorEnum getColor() {
        return color;
    }

    public PetOfferEntity setColor(ColorEnum color) {
        this.color = color;
        return this;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public PetOfferEntity setGender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PetOfferEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Double getWeight() {
        return weight;
    }

    public PetOfferEntity setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PetOfferEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getYears() {
        return years;
    }

    public PetOfferEntity setYears(int years) {
        this.years = years;
        return this;
    }

    public UserEntity getSeller() {
        return seller;
    }

    public PetOfferEntity setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }
}
