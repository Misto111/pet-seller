package com.project.petSeller.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "accessories-brands")
public class AccessoryBrandEntity extends BaseEntity{


    @Column(unique = true, nullable = false)
    private String name;


    @OneToMany(
            fetch = FetchType.LAZY,  //вземи моделите само, когато е необходимо
            cascade = CascadeType.ALL,
            mappedBy = "accessoryBrand"
    )
    private List<AccessoryModelEntity> model;

    public String getName() {
        return name;
    }

    public AccessoryBrandEntity setName(String name) {
        this.name = name;
        return this;
    }

    public List<AccessoryModelEntity> getModel() {
        return model;
    }

    public AccessoryBrandEntity setModel(List<AccessoryModelEntity> model) {
        this.model = model;
        return this;
    }
}
