package com.agile.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agile.petclinic.entities.Pet;

@SpringBootTest
class PetServiceTest {

	@Autowired
	private PetService service;

	static Pet pet1;
	static Pet pet2;
	static Pet pet3;

	@BeforeAll
	static void initAll() {
		pet1 = new Pet(1L, null, null, null, null);
		pet2 = new Pet(2L, null, null, null, null);
		pet3 = new Pet(3L, null, null, null, null);
	}

	@Test
	void shouldReturnAll() {
		List<Pet> list = Arrays.asList(pet1, pet2, pet3);
		assertEquals(list, service.findAll());
	}
	
	@Test
	void shouldReturnOne() {
		assertEquals(pet1, service.findById(pet1.getId()));
	}
	
	@Test
	void shouldInsertOne() {
		Pet pet4 = new Pet(null, null, null, null, null);
		Pet obj = service.insert(pet4);
		assertEquals(pet4, obj);
	}

	@Test
	void shouldDeleteOne() {
		service.delete(4L);
	}
	
	@Test
	@Transactional
	void shouldUpdateOne() {
		pet3.setBreed("Rottweiler");
		Pet obj = service.update(pet3.getId(), pet3);
		assertEquals(pet3.getBreed(), obj.getBreed());
	}
}
