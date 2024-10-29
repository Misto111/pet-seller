package com.project.petSeller.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "kinds")
@NamedEntityGraph(
        name = "kindWithBreeds",
        attributeNodes = @NamedAttributeNode("breeds")
)
public class KindEntity extends BaseEntity {


    @Column(unique = true, nullable = false)
    private String name;


    @OneToMany(
            fetch = FetchType.LAZY,  //вземи моделите само, когато е необходимо
            cascade = CascadeType.ALL,
            mappedBy = "kind"
    )
    private List<BreedEntity> breeds;


    public String getName() {
        return name;
    }

    public KindEntity setName(String name) {
        this.name = name;
        return this;
    }

    public List<BreedEntity> getBreeds() {
        return breeds;
    }

    public KindEntity setBreeds(List<BreedEntity> breeds) {
        this.breeds = breeds;
        return this;
    }


}
