package com.agile.petclinic.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.repositories.AppointmentRepository;
import com.agile.petclinic.services.exceptions.ResourceNotFoundException;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository repository;

	public List<Appointment> findAll() {
		return repository.findAll();
	}

	public Appointment findById(Long id) {
		Optional<Appointment> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Appointment insert(Appointment obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public Appointment update(Long id, Appointment obj) {
		try {
			Appointment entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Appointment entity, Appointment obj) {
		entity.setDatetime(obj.getDatetime());
		entity.setDescription(obj.getDescription());
		entity.setType(obj.getType());
		entity.setPet(obj.getPet());
		entity.setPayment(obj.getPayment());
	}
}
