package dev.arol.petclinic.config;

import dev.arol.petclinic.application.port.out.AppointmentRepository;
import dev.arol.petclinic.application.port.out.PetRepository;
import dev.arol.petclinic.application.usecase.AppointmentUseCaseImpl;
import dev.arol.petclinic.application.usecase.PetUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetClinicConfig {

    @Bean
    PetUseCaseImpl petUseCase(PetRepository petRepository) {
        return new PetUseCaseImpl(petRepository);
    }

    @Bean
    AppointmentUseCaseImpl appointmentUseCase(AppointmentRepository appointmentRepository, PetRepository petRepository) {
        return new AppointmentUseCaseImpl(appointmentRepository, petRepository);
    }
}
