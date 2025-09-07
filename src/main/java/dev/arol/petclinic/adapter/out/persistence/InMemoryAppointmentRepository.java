package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.application.port.out.AppointmentRepository;
import dev.arol.petclinic.domain.model.Appointment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("inmemory")
public class InMemoryAppointmentRepository implements AppointmentRepository {

    private final ConcurrentHashMap<Long, Appointment> appointments = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment.id() == null) {
            appointment = new Appointment(
                    idGenerator.getAndIncrement(),
                    appointment.petId(),
                    appointment.date(),
                    appointment.reason()
            );
        }
        appointments.put(appointment.id(), appointment);
        return appointment;
    }


    @Override
    public List<Appointment> findAll() {
        return appointments.values().stream().toList();
    }

}