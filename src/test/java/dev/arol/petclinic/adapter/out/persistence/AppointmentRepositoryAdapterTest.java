package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.domain.model.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ImportTestcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AppointmentRepositoryAdapter.class, dev.arol.petclinic.adapter.out.persistence.mapper.AppointmentMapperTestImpl.class})
@ActiveProfiles("postgres")
class AppointmentRepositoryAdapterTest extends PostgresContainerTestConfig {

    @Autowired
    private AppointmentRepositoryAdapter adapter;

    @Autowired
    private AppointmentRepositoryJpa jpa;

    @Test
    void save() {
        Appointment toSave = new Appointment(null, 1L, LocalDateTime.of(2025, 1, 1, 10, 0), "Check");

        Appointment saved = adapter.save(toSave);

        assertThat(saved.id()).isNotNull();
        assertThat(saved.petId()).isEqualTo(1L);
        assertThat(saved.reason()).isEqualTo("Check");

        // verify it is actually in DB
        assertThat(jpa.findById(saved.id())).isPresent();
    }

    @Test
    void findAll() {
        // seed two rows
        jpa.save(new AppointmentJpaEntity(null, 2L, LocalDateTime.of(2025, 2, 1, 10, 0), "A"));
        jpa.save(new AppointmentJpaEntity(null, 3L, LocalDateTime.of(2025, 2, 2, 11, 0), "B"));

        List<Appointment> list = adapter.findAll();
        assertThat(list).hasSizeGreaterThanOrEqualTo(2);
        assertThat(list).extracting(Appointment::reason).contains("A", "B");
    }
}