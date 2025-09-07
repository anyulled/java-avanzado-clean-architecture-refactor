package dev.arol.petclinic.adapter.in.web;

import dev.arol.petclinic.application.port.in.CreatePetUseCase;
import dev.arol.petclinic.application.port.in.GetPetUseCase;
import dev.arol.petclinic.domain.model.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final CreatePetUseCase createPetUseCase;
    private final GetPetUseCase getPetUseCase;

    public PetController(CreatePetUseCase createPetUseCase, GetPetUseCase getPetUseCase) {
        this.createPetUseCase = createPetUseCase;
        this.getPetUseCase = getPetUseCase;
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        Pet createdPet = createPetUseCase.createPet(pet);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = getPetUseCase.getAllPets();
        return ResponseEntity.ok(pets);
    }
}