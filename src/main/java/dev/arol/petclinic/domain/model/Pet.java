package dev.arol.petclinic.domain.model;

public record Pet(
        Long id,
        String name,
        String species,
        String ownerName
) {

    public boolean isValidForAppointment() {
        return name != null && !name.trim().isEmpty()
               && species != null && !species.trim().isEmpty();
    }

    public void validateForCreation() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet name is required");
        }
        if (species == null || species.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet species is mandatory");
        }
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet owner is mandatory");
        }
    }
}