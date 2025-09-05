package dev.arol.petclinic.config;

import dev.arol.petclinic.application.port.out.AppointmentRepository;
import dev.arol.petclinic.application.port.out.PetRepository;
import dev.arol.petclinic.domain.model.Appointment;
import dev.arol.petclinic.domain.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("inmemory")
public class InMemoryDataLoader implements CommandLineRunner {

    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public InMemoryDataLoader(PetRepository petRepository, AppointmentRepository appointmentRepository) {
        this.petRepository = petRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadSampleData();
    }

    private void loadSampleData() {
        // Insert sample pets
        Pet pet1 = petRepository.save(new Pet(null, "Buddy", "Dog", "John Smith"));
        Pet pet2 = petRepository.save(new Pet(null, "Whiskers", "Cat", "Jane Doe"));
        Pet pet3 = petRepository.save(new Pet(null, "Simba", "Dog", "Olga"));
        Pet pet4 = petRepository.save(new Pet(null, "Perchic", "Cat", "Arol"));
        Pet pet5 = petRepository.save(new Pet(null, "Charlie", "Rabbit", "David Brown"));
        Pet pet6 = petRepository.save(new Pet(null, "Bella", "Hamster", "Emma Davis"));
        Pet pet7 = petRepository.save(new Pet(null, "Milo", "Dog", "Lili Benitez"));
        Pet pet8 = petRepository.save(new Pet(null, "Daisy", "Bird", "Lisa Garcia"));

        // Insert sample appointments
        appointmentRepository.save(new Appointment(null, pet1.id(), LocalDateTime.of(2024, 2, 15, 10, 0), "Annual Checkup"));
        appointmentRepository.save(new Appointment(null, pet2.id(), LocalDateTime.of(2024, 2, 16, 14, 30), "Vaccination"));
        appointmentRepository.save(new Appointment(null, pet3.id(), LocalDateTime.of(2024, 2, 17, 9, 0), "Dental Cleaning"));
        appointmentRepository.save(new Appointment(null, pet1.id(), LocalDateTime.of(2024, 2, 20, 11, 0), "Follow-up Visit"));
        appointmentRepository.save(new Appointment(null, pet4.id(), LocalDateTime.of(2024, 2, 22, 15, 0), "Spay Surgery Consultation"));
        appointmentRepository.save(new Appointment(null, pet5.id(), LocalDateTime.of(2024, 2, 23, 16, 30), "Nail Trimming"));
        appointmentRepository.save(new Appointment(null, pet6.id(), LocalDateTime.of(2024, 2, 25, 8, 30), "Behavioral Assessment"));
        appointmentRepository.save(new Appointment(null, pet7.id(), LocalDateTime.of(2024, 2, 26, 13, 0), "Health Check"));

        System.out.println("Loaded sample data: " + petRepository.findAll().size() + " pets, " + appointmentRepository.findAll().size() + " appointments");
    }
}