package dev.arol.petclinic.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppoinmentRepositoryJpa extends JpaRepository<AppointmentJpaEntity, Long> {
}
