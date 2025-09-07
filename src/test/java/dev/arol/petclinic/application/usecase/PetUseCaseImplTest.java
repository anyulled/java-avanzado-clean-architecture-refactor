package dev.arol.petclinic.application.usecase;

import dev.arol.petclinic.application.port.out.PetRepository;
import dev.arol.petclinic.domain.model.Pet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PetUseCaseImplTest {

    @Mock
    PetRepository petRepository;

    @Test
    void createPetIsSuccessful() {

        var useCase = new PetUseCaseImpl(petRepository);
        var pet = new Pet(null, "Buddy", "Dog", "John Smith");
        Mockito.when(petRepository.save(pet)).thenReturn(pet);
        var createdPet = useCase.createPet(pet);
        Assertions.assertThat(createdPet).isNotNull()
                .extracting(Pet::name).isEqualTo("Buddy");
    }

    @Test
    void getAllPets() {
        var useCase = new PetUseCaseImpl(petRepository);
        var pets = List.of(new Pet(null, "Buddy", "Dog", "John Smith"), new Pet(null, "Max", "Cat", "Jane Doe"));
        Mockito.when(petRepository.findAll()).thenReturn(pets);
        var allPets = useCase.getAllPets();
        Assertions.assertThat(allPets).isNotNull()
                .hasSize(2)
                .extracting(Pet::name).contains("Buddy", "Max");
    }

    @Test
    void petExists() {
        var useCase = new PetUseCaseImpl(petRepository);
        var pet = new Pet(null, "Buddy", "Dog", "John Smith");
        Mockito.when(petRepository.existsById(pet.id())).thenReturn(true);
        Assertions.assertThat(useCase.petExists(pet.id())).isTrue();
    }
}