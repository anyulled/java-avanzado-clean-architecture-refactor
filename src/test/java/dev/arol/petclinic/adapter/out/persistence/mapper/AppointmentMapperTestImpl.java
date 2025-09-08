package dev.arol.petclinic.adapter.out.persistence.mapper;

import dev.arol.petclinic.adapter.out.persistence.AppointmentJpaEntity;
import dev.arol.petclinic.domain.model.Appointment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class AppointmentMapperTestImpl implements AppointmentMapper {
    @Override
    public Appointment toDomain(AppointmentJpaEntity entity) {
        if (entity == null) return null;
        return new Appointment(
                entity.getId(),
                entity.getPetId(),
                entity.getDate(),
                entity.getReason()
        );
    }

    @Override
    public AppointmentJpaEntity toEntity(Appointment domain) {
        if (domain == null) return null;
        return new AppointmentJpaEntity(
                domain.id(),
                domain.petId(),
                domain.date(),
                domain.reason()
        );
    }
}
