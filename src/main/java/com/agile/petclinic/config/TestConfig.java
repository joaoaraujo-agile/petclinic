package com.agile.petclinic.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.entities.Payment;
import com.agile.petclinic.entities.Pet;
import com.agile.petclinic.entities.User;
import com.agile.petclinic.entities.enums.AppointmentType;
import com.agile.petclinic.entities.enums.PaymentType;
import com.agile.petclinic.repositories.AppointmentRepository;
import com.agile.petclinic.repositories.PetRepository;
import com.agile.petclinic.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public void run(String... args) throws Exception {

		User usr1 = new User(null, "admin", "admin", pe.encode("admin123"));

		userRepository.save(usr1);

		Pet pet1 = new Pet(null, "Luna", LocalDate.of(2020, Month.MAY, 11), "Poodle", 'F');
		Pet pet2 = new Pet(null, "Charlie", LocalDate.of(2019, Month.JANUARY, 31), "Golden Retriever", 'M');
		Pet pet3 = new Pet(null, "Milo", LocalDate.of(2016, Month.AUGUST, 9), "Yorkshire", 'M');

		petRepository.saveAll(Arrays.asList(pet1, pet2, pet3));

		Appointment ap1 = new Appointment(null, Instant.parse("2021-05-13T12:00:00Z"), "Routine exam",
				AppointmentType.MEDICAL, pet1, null);
		Appointment ap2 = new Appointment(null, Instant.parse("2021-05-14T11:00:00Z"), "Surgery",
				AppointmentType.MEDICAL, pet2, null);
		Appointment ap3 = new Appointment(null, Instant.parse("2021-05-13T12:00:00Z"), "Shower and Grooming",
				AppointmentType.GROOMING, pet3, null);

		appointmentRepository.saveAll(Arrays.asList(ap1, ap2, ap3));

		Payment pay1 = new Payment(null, Instant.now(), 80.00, PaymentType.CASH, ap2);
		ap2.setPayment(pay1);
		Payment pay2 = new Payment(null, Instant.now(), 50.00, PaymentType.CASH, ap3);
		ap3.setPayment(pay2);
		appointmentRepository.saveAll(Arrays.asList(ap2, ap3));

	}

}
