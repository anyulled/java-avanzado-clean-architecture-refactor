package dev.arol.petclinic.domain.model;

import java.time.LocalDateTime;

public record Appointment(
        Long id,
        Long petId,
        LocalDateTime date,
        String reason
) {
    public void validateForCreation() {
        if (petId == null) {
            throw new IllegalArgumentException("Pet ID is required");
        }
        if (date == null) {
            throw new IllegalArgumentException("Appointment date is required");

        }
    }
}