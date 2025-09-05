package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.application.port.out.AppointmentRepository;
import dev.arol.petclinic.domain.model.Appointment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"h2", "postgres"})
public class AppointmentRepositoryAdapter implements AppointmentRepository {

    private final AppoinmentRepositoryJpa appointmentRepositoryJpa;

    public AppointmentRepositoryAdapter(AppoinmentRepositoryJpa appointmentRepositoryJpa) {
        this.appointmentRepositoryJpa = appointmentRepositoryJpa;
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentJpaEntity entity = AppointmentJpaEntity.fromDomain(appointment);
        return appointmentRepositoryJpa.save(entity).toDomain();
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepositoryJpa.findAll()
                .stream()
                .map(AppointmentJpaEntity::toDomain)
                .toList();
    }
}
