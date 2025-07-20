package dev.arol.petclinic.service;

import dev.arol.petclinic.entity.Appointment;
import dev.arol.petclinic.repository.IAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {
    
    private final IAppointmentRepository appointmentRepository;
    private final PetService petService;

    @Autowired
    public AppointmentService(IAppointmentRepository appointmentRepository, PetService petService) {
        this.appointmentRepository = appointmentRepository;
        this.petService = petService;
    }

    public Appointment createAppointment(Appointment appointment) {
        if (!petService.petExists(appointment.getPetId())) {
            throw new IllegalArgumentException("Pet with ID " + appointment.getPetId() + " does not exist");
        }
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}