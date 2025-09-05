package dev.arol.petclinic.application.port.in;

import dev.arol.petclinic.domain.model.Pet;

public interface CreatePetUseCase {
    Pet createPet(Pet pet);
}
