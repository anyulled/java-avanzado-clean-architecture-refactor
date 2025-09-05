package dev.arol.petclinic.application.usecase;

import dev.arol.petclinic.application.port.in.CreateAppointmentUseCase;
import dev.arol.petclinic.application.port.in.GetAppointmentUseCase;
import dev.arol.petclinic.application.port.out.AppointmentRepository;
import dev.arol.petclinic.application.port.out.PetRepository;
import dev.arol.petclinic.domain.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentUseCaseImpl implements CreateAppointmentUseCase, GetAppointmentUseCase {

    AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;

    @Autowired
    public AppointmentUseCaseImpl(AppointmentRepository appointmentRepository, PetRepository petRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        appointment.validateForCreation();

        if (!petRepository.existsById(appointment.petId())) {
            throw new IllegalArgumentException("Pet with ID " + appointment.petId() + " does not exist");
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
