package dev.arol.petclinic.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;


class PetTest {

    @Test
    void isValidForAppointment() {
        var pet = new Pet(null, "Buddy", "Dog", "John Smith");
        assertThat(pet.isValidForAppointment()).isTrue();
    }

    @Test
    void isNotValidForAppointment() {
        var pet = new Pet(null, null, "Dog", "John Smith");
        assertThat(pet.isValidForAppointment()).isFalse();
    }

    @Test
    void validateForCreationThrowsExceptionWhenNameIsNull() {
        var pet = new Pet(null, null, "German Shepperd", "Owner");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(pet::validateForCreation)
                .withMessageContaining("Pet name is required");
    }

    @Test
    void validateForCreationThrowsExceptionWhenNameIsEmpty() {
        var pet = new Pet(null, "", "German Shepperd", "Owner");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(pet::validateForCreation)
                .withMessageContaining("Pet name is required");
    }

    @Test
    void validateForCreationIsSuccessful() {
        var pet = new Pet(null, "Buddy", "Dog", "John Smith");
        assertThatNoException().isThrownBy(pet::validateForCreation);
    }
}