package dev.arol.petclinic.repository;

import dev.arol.petclinic.entity.Appointment;

import java.util.List;
import java.util.Optional;

public interface IAppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
    long count();
}