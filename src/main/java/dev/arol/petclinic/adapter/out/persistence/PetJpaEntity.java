package dev.arol.petclinic.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pets")
public class PetJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String species;
    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    public PetJpaEntity(Long id, String name, String species, String ownerName) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.ownerName = ownerName;

    }

    public PetJpaEntity() {

    }

    //<editor-fold desc="Getters and setters">
    public Long getId() {
        return id;
    }

    public PetJpaEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PetJpaEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getSpecies() {
        return species;
    }

    public PetJpaEntity setSpecies(String species) {
        this.species = species;
        return this;
    }

    public String ownerName() {
        return ownerName;
    }
    //</editor-fold>

    public PetJpaEntity setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }
}
