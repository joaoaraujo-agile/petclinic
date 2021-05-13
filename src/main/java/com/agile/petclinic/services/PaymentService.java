package com.agile.petclinic.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.agile.petclinic.dto.PaymentRequestDTO;
import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.entities.Payment;
import com.agile.petclinic.repositories.AppointmentRepository;
import com.agile.petclinic.repositories.PaymentRepository;
import com.agile.petclinic.services.exceptions.ResourceNotFoundException;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository repository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	public List<Payment> findAll() {
		return repository.findAll();
	}

	public Payment findById(Long id) {
		Optional<Payment> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Payment insert(PaymentRequestDTO obj) {
		Appointment appointment = appointmentRepository.getOne(obj.getAppointmentId());
		appointment.setPayment(new Payment(null, obj.getDatetime(), obj.getAmount(), obj.getType(), appointment));
		appointmentRepository.save(appointment);
		return appointment.getPayment();
	}

	public void delete(Long id) {
		try {
			Appointment appointment = appointmentRepository.getOne(id);
			appointment.setPayment(null);
			appointmentRepository.save(appointment);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public Payment update(Long id, Payment obj) {
		try {
			Payment entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Payment entity, Payment obj) {
		entity.setDatetime(obj.getDatetime());
		entity.setAmount(obj.getAmount());
		entity.setType(obj.getType());
	}
}
