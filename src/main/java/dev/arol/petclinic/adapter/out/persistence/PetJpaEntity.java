package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.domain.model.Pet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Entity
@Table(name = "pets")
public class PetJpaEntity {

    private static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();
    private static final BoundMapperFacade<PetJpaEntity, Pet> TO_DOMAIN_MAPPER;
    private static final BoundMapperFacade<Pet, PetJpaEntity> TO_ENTITY_MAPPER;

    static {
        TO_DOMAIN_MAPPER = MAPPER_FACTORY.getMapperFacade(PetJpaEntity.class, Pet.class);
        TO_ENTITY_MAPPER = MAPPER_FACTORY.getMapperFacade(Pet.class, PetJpaEntity.class);
    }

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

    public static PetJpaEntity fromDomain(Pet pet) {
        return TO_ENTITY_MAPPER.map(pet);
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

    //<editor-fold desc="Mapper">
    public Pet toDomain() {
        return TO_DOMAIN_MAPPER.map(this);
    }
    //</editor-fold>
}
