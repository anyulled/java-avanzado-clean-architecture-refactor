package dev.arol.petclinic.adapter.out.persistence.mapper;

import dev.arol.petclinic.adapter.out.persistence.PetJpaEntity;
import dev.arol.petclinic.domain.model.Pet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {
    Pet toDomain(PetJpaEntity entity);

    PetJpaEntity toEntity(Pet domain);
}
