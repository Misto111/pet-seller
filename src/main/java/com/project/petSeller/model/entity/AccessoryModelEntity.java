package com.project.petSeller.model.entity;

import com.project.petSeller.model.enums.AccessoryModelCategoryEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "accessories-models")
public class AccessoryModelEntity extends BaseEntity{

    private String name;
    @Enumerated(EnumType.STRING)
    private AccessoryModelCategoryEnum category;
    @ManyToOne
    private AccessoryBrandEntity accessoryBrand;

    public String getName() {
        return name;
    }

    public AccessoryModelEntity setName(String name) {
        this.name = name;
        return this;
    }

    public AccessoryModelCategoryEnum getCategory() {
        return category;
    }

    public AccessoryModelEntity setCategory(AccessoryModelCategoryEnum category) {
        this.category = category;
        return this;
    }

    public AccessoryBrandEntity getAccessoryBrand() {
        return accessoryBrand;
    }

    public AccessoryModelEntity setAccessoryBrand(AccessoryBrandEntity accessoryBrand) {
        this.accessoryBrand = accessoryBrand;
        return this;
    }

    @Override
    public String toString() {
        return "AccessoryModelEntity{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", accessoryBrand=" + accessoryBrand +
                '}';
    }
}
