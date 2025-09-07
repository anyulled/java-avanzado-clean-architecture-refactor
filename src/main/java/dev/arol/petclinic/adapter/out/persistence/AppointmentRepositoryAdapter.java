package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.adapter.out.persistence.mapper.AppointmentMapper;
import dev.arol.petclinic.application.port.out.AppointmentRepository;
import dev.arol.petclinic.domain.model.Appointment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"h2", "postgres"})
public class AppointmentRepositoryAdapter implements AppointmentRepository {

    private final AppoinmentRepositoryJpa appointmentRepositoryJpa;
    private final AppointmentMapper mapper;

    public AppointmentRepositoryAdapter(AppoinmentRepositoryJpa appointmentRepositoryJpa,
                                        AppointmentMapper mapper) {
        this.appointmentRepositoryJpa = appointmentRepositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentJpaEntity entity = mapper.toEntity(appointment);
        return mapper.toDomain(appointmentRepositoryJpa.save(entity));
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepositoryJpa.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
