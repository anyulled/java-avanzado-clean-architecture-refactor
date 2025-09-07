package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.domain.model.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AppointmentRepositoryAdapter.class, dev.arol.petclinic.adapter.out.persistence.mapper.AppointmentMapperTestImpl.class})
@ActiveProfiles("postgres")
class AppoinmentRepositoryJpaTest extends PostgresContainerTestConfig {

    @Autowired
    private AppoinmentRepositoryJpa jpa;

    @Test
    void shouldSaveAndRetrieve() {
        // Save a domain appointment with a simple petId reference (no FK constraint defined)
        Appointment toSave = new Appointment(null, 1L, LocalDateTime.now(), "annual check");

        AppointmentJpaEntity saved = jpa.save(new AppointmentJpaEntity(null, toSave.petId(), toSave.date(), toSave.reason()));

        assertThat(saved).isNotNull();

        var retrieved = jpa.findAll();
        assertThat(retrieved).isNotEmpty();
        assertThat(retrieved.get(0).getPetId()).isEqualTo(saved.getPetId());
    }
}