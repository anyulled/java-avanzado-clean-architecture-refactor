package dev.arol.petclinic.application.usecase;

import dev.arol.petclinic.application.port.out.AppointmentRepository;
import dev.arol.petclinic.application.port.out.PetRepository;
import dev.arol.petclinic.domain.model.Appointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentUseCaseImplTest {

    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    PetRepository petRepository;

    @Test
    void createAppointmentSuccessfully() {
        var useCase = new AppointmentUseCaseImpl(appointmentRepository, petRepository);

        var now = LocalDateTime.now();

        var appointment = new Appointment(1L, 1L, now, "annual check");
        when(petRepository.existsById(appointment.petId())).thenReturn(true);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        var created = useCase.createAppointment(appointment);

        assertThat(created)
                .isNotNull()
                .returns(1L, Appointment::id)
                .returns(1L, Appointment::petId)
                .returns("annual check", Appointment::reason)
                .returns(now, Appointment::date);

        verify(appointmentRepository, times(1)).save(appointment);
        verifyNoMoreInteractions(appointmentRepository);
        verify(petRepository, times(1)).existsById(appointment.petId());
    }

    @Test
    void createAnAppointmentWithInexistentPet() {
        var useCase = new AppointmentUseCaseImpl(appointmentRepository, petRepository);
        var appointment = new Appointment(1L, 1L, LocalDateTime.now(), "annual check");
        when(petRepository.existsById(appointment.petId())).thenReturn(false);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> useCase.createAppointment(appointment))
                .withMessageContaining("Pet with ID 1 does not exist");

        verify(petRepository, times(1)).existsById(appointment.petId());
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    void getAllAppointmentsSuccessfully() {
        var useCase = new AppointmentUseCaseImpl(appointmentRepository, petRepository);
        var appointments = List.of(new Appointment(1L, 1L, LocalDateTime.now(), "annual check"),
                new Appointment(2L, 2L, LocalDateTime.now(), "vaccination"));
        Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);
        var allAppointments = useCase.getAllAppointments();
        assertThat(allAppointments)
                .isNotNull()
                .hasSize(2)
                .containsExactlyElementsOf(appointments);

        verify(appointmentRepository, times(1)).findAll();
        verifyNoMoreInteractions(appointmentRepository);
    }
}