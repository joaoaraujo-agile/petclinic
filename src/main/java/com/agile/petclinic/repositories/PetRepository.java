package com.agile.petclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agile.petclinic.entities.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
