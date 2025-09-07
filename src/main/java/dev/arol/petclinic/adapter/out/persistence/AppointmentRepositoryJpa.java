package dev.arol.petclinic.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepositoryJpa extends JpaRepository<AppointmentJpaEntity, Long> {
}
