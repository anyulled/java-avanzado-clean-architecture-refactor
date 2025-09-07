package dev.arol.petclinic.application.port.in;

import dev.arol.petclinic.domain.model.Appointment;

public interface CreateAppointmentUseCase {
    Appointment createAppointment(Appointment appointment);
}
