package com.agile.petclinic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agile.petclinic.entities.Pet;
import com.agile.petclinic.exceptions.ResourceNotFoundException;
import com.agile.petclinic.repositories.PetRepository;

@Service
public class PetService {

	@Autowired
	private PetRepository repository;

	public List<Pet> findAll() {
		return repository.findAll();
	}
	
	public Pet findById(Long id) {
		Optional<Pet> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Pet insert(Pet obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Pet update(Long id, Pet obj) {
		Pet entity = repository.getOne(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Pet entity, Pet obj) {
		entity.setName(obj.getName());
		entity.setBirthdate(obj.getBirthdate());
		entity.setBreed(obj.getBreed());
		entity.setGender(obj.getGender());
		
	}
}
