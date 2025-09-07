package dev.arol.petclinic.application.port.in;

import dev.arol.petclinic.domain.model.Pet;

import java.util.List;

public interface GetPetUseCase {
    List<Pet> getAllPets();
}
