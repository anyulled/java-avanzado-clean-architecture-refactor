package dev.arol.petclinic.application.usecase;

import dev.arol.petclinic.application.port.in.CreatePetUseCase;
import dev.arol.petclinic.application.port.in.GetPetUseCase;
import dev.arol.petclinic.application.port.in.PetExistsUseCase;
import dev.arol.petclinic.application.port.out.PetRepository;
import dev.arol.petclinic.domain.model.Pet;

import java.util.List;

public class PetUseCaseImpl implements CreatePetUseCase, GetPetUseCase, PetExistsUseCase {

    private final PetRepository petRepository;

    public PetUseCaseImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public boolean petExists(Long petId) {
        return petRepository.existsById(petId);
    }
}
