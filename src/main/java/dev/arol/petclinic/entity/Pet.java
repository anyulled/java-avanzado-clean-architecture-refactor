package dev.arol.petclinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "Pet name is required")
    private String name;
    
    @Column(nullable = false)
    @NotBlank(message = "Pet species is required")
    private String species;
    
    @Column(name = "owner_name", nullable = false)
    @NotBlank(message = "Owner name is required")
    private String ownerName;

    public Pet() {}

    public Pet(Long id, String name, String species, String ownerName) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.ownerName = ownerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}