package dev.arol.petclinic.repository;

import dev.arol.petclinic.entity.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"h2", "postgres"})
public interface PetRepository extends JpaRepository<Pet, Long>, IPetRepository {
}