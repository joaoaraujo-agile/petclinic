package com.agile.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.entities.Pet;
import com.agile.petclinic.entities.enums.AppointmentType;

@SpringBootTest
class AppointmentServiceTest {

	@Autowired
	private AppointmentService service;

	static Pet pet1;
	static Pet pet2;
	static Pet pet3;
	
	static Appointment ap1;
	static Appointment ap2;
	static Appointment ap3;

	@BeforeAll
	static void initAll() {
		pet1 = new Pet(1L, null, null, null, null);
		pet2 = new Pet(2L, null, null, null, null);
		pet3 = new Pet(3L, null, null, null, null);
		
		ap1 = new Appointment(1L, Instant.parse("2021-05-13T12:00:00Z"), "Routine exam", AppointmentType.MEDICAL, pet1, null);
		ap2 = new Appointment(2L, Instant.parse("2021-05-14T11:00:00Z"), "Surgery", AppointmentType.MEDICAL, pet2, null);
		ap3 = new Appointment(3L, Instant.parse("2021-05-13T12:00:00Z"), "Shower and Grooming", AppointmentType.GROOMING, pet3, null);
	}

	@Test
	void shouldReturnAll() {
		List<Appointment> list = Arrays.asList(ap1, ap2, ap3);
		assertEquals(list, service.findAll());
	}
	
	@Test
	void shouldReturnOne() {
		assertEquals(ap1, service.findById(ap1.getId()));
	}
	
	@Test
	void shouldInsertOne() {
		Appointment ap4 = new Appointment(null, null, null, null, pet1, null);
		Appointment obj = service.insert(ap4);
		assertEquals(ap4, obj);
	}

	@Test
	void shouldDeleteOne() {
		service.delete(4L);
	}
	
	@Test
	@Transactional
	void shouldUpdateOne() {
		ap3.setDescription("Grooming");
		Appointment obj = service.update(ap3.getId(), ap3);
		assertEquals(ap3.getDescription(), obj.getDescription());
	}
}
