package dev.arol.petclinic.application.port.out;

import dev.arol.petclinic.domain.model.Appointment;

import java.util.List;

public interface AppointmentRepository {

    Appointment save(Appointment appointment);

    List<Appointment> findAll();
}
