package dev.arol.petclinic.application.usecase;

import dev.arol.petclinic.application.port.in.CreateAppointmentUseCase;
import dev.arol.petclinic.application.port.in.GetAppointmentUseCase;
import dev.arol.petclinic.application.port.out.AppointmentRepository;
import dev.arol.petclinic.domain.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentUseCaseImpl implements CreateAppointmentUseCase, GetAppointmentUseCase {

    AppointmentRepository appointmentRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
