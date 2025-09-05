package dev.arol.petclinic.application.port.out;

import dev.arol.petclinic.domain.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    Pet save(Pet pet);

    List<Pet> findAll();

    Optional<Pet> findById(Long id);

    boolean existsById(Long id);
}
