package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.adapter.out.persistence.mapper.PetMapperTestImpl;
import dev.arol.petclinic.domain.model.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({PetRepositoryAdapter.class, PetMapperTestImpl.class})
@ActiveProfiles("postgres")
class PetRepositoryAdapterTest extends PostgresContainerTestConfig {

    @Autowired
    private PetRepositoryAdapter adapter;

    @Autowired
    private PetRepositoryJpa jpa;

    @Test
    void save() {
        Pet toSave = new Pet(null, "Fluffy", "Cat", "Alice");

        Pet saved = adapter.save(toSave);

        assertThat(saved.id()).isNotNull();
        assertThat(saved.name()).isEqualTo("Fluffy");
        assertThat(saved.species()).isEqualTo("Cat");
        assertThat(saved.ownerName()).isEqualTo("Alice");

        assertThat(jpa.findById(saved.id())).isPresent();
    }

    @Test
    void findAll() {
        jpa.save(new PetJpaEntity(null, "Bella", "Dog", "Bob"));
        jpa.save(new PetJpaEntity(null, "Milo", "Cat", "Carol"));

        List<Pet> list = adapter.findAll();
        assertThat(list).hasSizeGreaterThanOrEqualTo(2);
        assertThat(list).extracting(Pet::name).contains("Bella", "Milo");
    }
}
