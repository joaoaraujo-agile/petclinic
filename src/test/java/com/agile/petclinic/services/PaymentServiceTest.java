package com.agile.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agile.petclinic.dto.PaymentRequestDTO;
import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.entities.Payment;
import com.agile.petclinic.entities.Pet;
import com.agile.petclinic.entities.enums.AppointmentType;
import com.agile.petclinic.entities.enums.PaymentType;

@SpringBootTest
@Transactional
class PaymentServiceTest {

	@Autowired
	private PaymentService service;

	static Payment pay1;
	static Payment pay2;	
	
	static Pet pet2;
	static Pet pet3;

	static Appointment ap2;
	static Appointment ap3;

	@BeforeAll
	static void initAll() {
		pet2 = new Pet(2L, "Charlie", LocalDate.of(2019, Month.JANUARY, 31), "Golden Retriever", 'M');
		pet3 = new Pet(3L, "Milo", LocalDate.of(2016, Month.AUGUST, 9), "Yorkshire", 'M');
		
		ap2 = new Appointment(2L, Instant.parse("2021-05-14T11:00:00Z"), "Surgery", AppointmentType.MEDICAL, pet2,
				pay1);
		ap3 = new Appointment(3L, Instant.parse("2021-05-13T12:00:00Z"), "Shower and Grooming",
				AppointmentType.GROOMING, pet3, pay2);
		
		pay1 = new Payment(2L, Instant.now(), 80.00, PaymentType.CASH, ap2);
		pay2 = new Payment(3L, Instant.now(), 50.00, PaymentType.CASH, ap3);
	}

	@Test
	void shouldReturnAll() {
		List<Payment> list = Arrays.asList(pay1, pay2);
		assertEquals(list, service.findAll());
	}

	@Test
	void shouldReturnOne() {
		assertEquals(pay1, service.findById(pay1.getId()));
	}

	@Test
	void shouldInsertOne() {
		PaymentRequestDTO payReq = new PaymentRequestDTO(Instant.now(), 200.00, PaymentType.CASH, 1L);
		Payment obj = service.insert(payReq);
		assertEquals(1L, obj.getId());
	}

	@Test
	void shouldDeleteOne() {
		service.delete(1L);
	}

	@Test
	void shouldUpdateOne() {
		pay2.setAmount(30.00);
		Payment obj = service.update(pay2.getId(), pay2);
		assertEquals(pay2.getAmount(), obj.getAmount());
	}
}
