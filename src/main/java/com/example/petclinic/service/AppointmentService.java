package com.example.petclinic.service;

import com.example.petclinic.entity.Appointment;
import com.example.petclinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final PetService petService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, PetService petService) {
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