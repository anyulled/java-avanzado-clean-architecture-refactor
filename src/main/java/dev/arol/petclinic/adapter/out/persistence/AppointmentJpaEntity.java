package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.adapter.out.persistence.mapper.AppointmentMapper;
import dev.arol.petclinic.domain.model.Appointment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class AppointmentJpaEntity {

    private static final AppointmentMapper MAPPER = Mappers.getMapper(AppointmentMapper.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pet_id", nullable = false)
    private Long petId;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private String reason;

    public AppointmentJpaEntity() {
    }

    public AppointmentJpaEntity(Long id, Long petId, LocalDateTime date, String reason) {
        this.id = id;
        this.petId = petId;
        this.date = date;
        this.reason = reason;
    }

    public static AppointmentJpaEntity fromDomain(Appointment appointment) {
        return MAPPER.toEntity(appointment);
    }

    //<editor-fold desc="getters & setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }
    //</editor-fold>

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Appointment toDomain() {
        return MAPPER.toDomain(this);
    }
}
