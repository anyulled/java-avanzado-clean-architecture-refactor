package dev.arol.petclinic.adapter.out.persistence.mapper;

import dev.arol.petclinic.adapter.out.persistence.PetJpaEntity;
import dev.arol.petclinic.domain.model.Pet;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PetMapperTestImpl implements PetMapper {
    @Override
    public Pet toDomain(PetJpaEntity entity) {
        if (entity == null) return null;
        return new Pet(
                entity.getId(),
                entity.getName(),
                entity.getSpecies(),
                entity.ownerName()
        );
    }

    @Override
    public PetJpaEntity toEntity(Pet domain) {
        if (domain == null) return null;
        return new PetJpaEntity(
                domain.id(),
                domain.name(),
                domain.species(),
                domain.ownerName()
        );
    }
}
