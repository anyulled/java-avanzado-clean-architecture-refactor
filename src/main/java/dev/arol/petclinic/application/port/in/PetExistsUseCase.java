package dev.arol.petclinic.application.port.in;

public interface PetExistsUseCase {
    boolean petExists(Long petId);
}
