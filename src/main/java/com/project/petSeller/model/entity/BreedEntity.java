package com.project.petSeller.model.entity;

import com.project.petSeller.model.enums.BreedCategoryEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "breeds")
public class BreedEntity extends BaseEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private BreedCategoryEnum category;
    @ManyToOne
    private KindEntity kind;

    public String getName() {
        return name;
    }

    public BreedEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BreedCategoryEnum getCategory() {
        return category;
    }

    public BreedEntity setCategory(BreedCategoryEnum category) {
        this.category = category;
        return this;
    }

    public KindEntity getKind() {
        return kind;
    }

    public BreedEntity setKind(KindEntity kind) {
        this.kind = kind;
        return this;
    }

    @Override
    public String toString() {
        return "BreedEntity{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", kind=" + kind +
                '}';
    }
}




