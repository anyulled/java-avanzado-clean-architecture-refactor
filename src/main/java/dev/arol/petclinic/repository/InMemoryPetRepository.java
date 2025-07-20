package dev.arol.petclinic.repository;

import dev.arol.petclinic.entity.Pet;
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
        if (pet.getId() == null) {
            pet.setId(idGenerator.getAndIncrement());
        }
        pets.put(pet.getId(), pet);
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