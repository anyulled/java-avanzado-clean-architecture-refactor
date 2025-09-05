package dev.arol.petclinic.adapter.out.persistence;

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

    public PetRepositoryAdapter(PetRepositoryJpa petRepositoryJpa) {
        this.petRepositoryJpa = petRepositoryJpa;
    }

    @Override
    public Pet save(Pet pet) {
        PetJpaEntity entity = PetJpaEntity.fromDomain(pet);
        return petRepositoryJpa.save(entity).toDomain();
    }

    @Override
    public List<Pet> findAll() {
        return petRepositoryJpa.findAll()
                .stream()
                .map(PetJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petRepositoryJpa
                .findById(id)
                .map(PetJpaEntity::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return petRepositoryJpa.existsById(id);
    }
}
