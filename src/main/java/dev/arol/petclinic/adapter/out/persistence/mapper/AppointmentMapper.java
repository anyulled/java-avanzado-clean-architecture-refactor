package dev.arol.petclinic.adapter.out.persistence.mapper;

import dev.arol.petclinic.adapter.out.persistence.AppointmentJpaEntity;
import dev.arol.petclinic.domain.model.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    Appointment toDomain(AppointmentJpaEntity entity);
    AppointmentJpaEntity toEntity(Appointment domain);
}
