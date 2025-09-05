package dev.arol.petclinic.application.port.in;

import dev.arol.petclinic.domain.model.Appointment;

import java.util.List;

public interface GetAppointmentUseCase {
    List<Appointment> getAllAppointments();
}
