package dev.arol.petclinic.adapter.out.persistence;

import dev.arol.petclinic.domain.model.Pet;
import dev.arol.petclinic.repository.IPetRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("inmemory")
public class InMemoryPetRepository implements IPetRepository {

    private final ConcurrentHashMap<Long, Pet> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Pet save(Pet pet) {
        if (pet.id() == null) {
            pet = new Pet(idGenerator.getAndIncrement(),
                    pet.name(),
                    pet.species(),
                    pet.ownerName());
        }
        pets.put(pet.id(), pet);
        return pet;
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    @Override
    public List<Pet> findAll() {
        return pets.values().stream().toList();
    }

    @Override
    public boolean existsById(Long id) {
        return pets.containsKey(id);
    }

    @Override
    public void deleteById(Long id) {
        pets.remove(id);
    }

    @Override
    public long count() {
        return pets.size();
    }
}