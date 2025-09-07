package dev.arol.petclinic.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AppointmentTest {

    @Test
    void validateForCreationThrowsExceptionWhenPetIdIsNull() {
        var appointment = new Appointment(null, null, null, null);
        Assertions
                .assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(appointment::validateForCreation)
                .withMessageContaining("Pet ID is required");
    }

    @Test
    void validateForCreationIsSuccessful() {
        var appointment = new Appointment(1L, 1L, LocalDateTime.now(), "Annual check");
        Assertions.assertThatNoException().isThrownBy(appointment::validateForCreation);
    }
}