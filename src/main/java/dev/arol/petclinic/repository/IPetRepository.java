package dev.arol.petclinic.repository;

import dev.arol.petclinic.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface IPetRepository {
    Pet save(Pet pet);
    Optional<Pet> findById(Long id);
    List<Pet> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
    long count();
}