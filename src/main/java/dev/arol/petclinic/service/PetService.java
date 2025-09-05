package dev.arol.petclinic.service;

import dev.arol.petclinic.domain.model.Pet;
import dev.arol.petclinic.repository.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    
    private final IPetRepository petRepository;

    @Autowired
    public PetService(IPetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public boolean petExists(Long petId) {
        return petRepository.findById(petId).isPresent();
    }
}