package dev.arol.petclinic.adapter.out.persistence.mapper;

import dev.arol.petclinic.adapter.out.persistence.PetJpaEntity;
import dev.arol.petclinic.domain.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetMapper {

    // MapStruct might not detect the unconventional getter ownerName(); map explicitly via expression
    @Mapping(target = "ownerName", expression = "java(entity.ownerName())")
    Pet toDomain(PetJpaEntity entity);

    PetJpaEntity toEntity(Pet domain);
}
