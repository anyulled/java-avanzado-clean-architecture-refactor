package dev.arol.petclinic.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepositoryJpa extends JpaRepository<PetJpaEntity, Long> {
}
