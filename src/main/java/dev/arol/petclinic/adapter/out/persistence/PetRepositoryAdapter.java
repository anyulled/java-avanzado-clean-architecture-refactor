package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.adapter.out.persistence.mapper.PetMapper;
import dev.arol.petclinic.application.port.out.PetRepository;
import dev.arol.petclinic.domain.model.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile({"h2", "postgres"})
public class PetRepositoryAdapter implements PetRepository {

    private final PetRepositoryJpa petRepositoryJpa;
    private final PetMapper mapper;

    public PetRepositoryAdapter(PetRepositoryJpa petRepositoryJpa,
                                PetMapper mapper) {
        this.petRepositoryJpa = petRepositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public Pet save(Pet pet) {
        PetJpaEntity entity = mapper.toEntity(pet);
        return mapper.toDomain(petRepositoryJpa.save(entity));
    }

    @Override
    public List<Pet> findAll() {
        return petRepositoryJpa.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petRepositoryJpa
                .findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return petRepositoryJpa.existsById(id);
    }
}
