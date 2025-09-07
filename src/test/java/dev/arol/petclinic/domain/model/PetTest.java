package dev.arol.petclinic.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class PetTest {

    @Test
    void isValidForAppointment() {
        var pet = new Pet(null, "Buddy", "Dog", "John Smith");
        Assertions.assertThat(pet.isValidForAppointment()).isTrue();
    }

    @Test
    void isNotValidForAppointment() {
        var pet = new Pet(null, null, "Dog", "John Smith");
        Assertions.assertThat(pet.isValidForAppointment()).isFalse();
    }

    @Test
    void validateForCreationThrowsExceptionWhenNameIsNull() {
        var pet = new Pet(null, null, "German Shepperd", "Owner");
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(pet::validateForCreation)
                .withMessageContaining("Pet name is required");
    }

    @Test
    void validateForCreationThrowsExceptionWhenNameIsEmpty() {
        var pet = new Pet(null, "", "German Shepperd", "Owner");
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(pet::validateForCreation)
                .withMessageContaining("Pet name is required");
    }

    @Test
    void validateForCreationIsSuccessful() {
        var pet = new Pet(null, "Buddy", "Dog", "John Smith");
        Assertions.assertThatNoException().isThrownBy(pet::validateForCreation);
    }
}