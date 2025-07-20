package dev.arol.petclinic.config;

import dev.arol.petclinic.entity.Appointment;
import dev.arol.petclinic.entity.Pet;
import dev.arol.petclinic.repository.IAppointmentRepository;
import dev.arol.petclinic.repository.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("inmemory")
public class InMemoryDataLoader implements CommandLineRunner {

    private final IPetRepository petRepository;
    private final IAppointmentRepository appointmentRepository;

    @Autowired
    public InMemoryDataLoader(IPetRepository petRepository, IAppointmentRepository appointmentRepository) {
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
        appointmentRepository.save(new Appointment(null, pet1.getId(), LocalDateTime.of(2024, 2, 15, 10, 0), "Annual Checkup"));
        appointmentRepository.save(new Appointment(null, pet2.getId(), LocalDateTime.of(2024, 2, 16, 14, 30), "Vaccination"));
        appointmentRepository.save(new Appointment(null, pet3.getId(), LocalDateTime.of(2024, 2, 17, 9, 0), "Dental Cleaning"));
        appointmentRepository.save(new Appointment(null, pet1.getId(), LocalDateTime.of(2024, 2, 20, 11, 0), "Follow-up Visit"));
        appointmentRepository.save(new Appointment(null, pet4.getId(), LocalDateTime.of(2024, 2, 22, 15, 0), "Spay Surgery Consultation"));
        appointmentRepository.save(new Appointment(null, pet5.getId(), LocalDateTime.of(2024, 2, 23, 16, 30), "Nail Trimming"));
        appointmentRepository.save(new Appointment(null, pet6.getId(), LocalDateTime.of(2024, 2, 25, 8, 30), "Behavioral Assessment"));
        appointmentRepository.save(new Appointment(null, pet7.getId(), LocalDateTime.of(2024, 2, 26, 13, 0), "Health Check"));

        System.out.println("Loaded sample data: " + petRepository.count() + " pets, " + appointmentRepository.count() + " appointments");
    }
}