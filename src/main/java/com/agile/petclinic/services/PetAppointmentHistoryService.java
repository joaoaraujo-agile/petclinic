package com.agile.petclinic.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.entities.PetAppointmentHistory;
import com.agile.petclinic.queues.PetAppointmentHistorySender;
import com.agile.petclinic.repositories.AppointmentRepository;
import com.agile.petclinic.repositories.PetAppointmentHistoryRepository;
import com.agile.petclinic.services.exceptions.ResourceNotFoundException;

@Service
public class PetAppointmentHistoryService {

	@Autowired
	private PetAppointmentHistoryRepository repository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private PetAppointmentHistorySender sender;

	public List<PetAppointmentHistory> findAll() {
		return repository.findAll();
	}

	public PetAppointmentHistory findById(Long id) {
		Optional<PetAppointmentHistory> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public List<PetAppointmentHistory> getPetHistory(Long petId) {
		List<PetAppointmentHistory> history = repository.findAll().stream()
				.filter(pah -> pah.getAppointment().getPet().getId() == petId).collect(Collectors.toList());
		return history;
	}

	public PetAppointmentHistory insert(Long appointmentId) {
		try {			
			Appointment obj = appointmentRepository.findById(appointmentId).orElseThrow(() -> new ResourceNotFoundException(appointmentId));
			obj.setHistory(new PetAppointmentHistory(null, obj));			
			appointmentRepository.save(obj);
			return obj.getHistory();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public PetAppointmentHistory update(Long id) {
		try {
			PetAppointmentHistory entity = repository.getOne(id);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public void send(Appointment obj) {
		sender.send(obj.getId().toString());
	}

	public void verifyHistory() {
		if (repository.count() != appointmentRepository.count()) {
			List<Appointment> list = appointmentRepository.findAll().stream().filter(a -> a.getHistory() == null).collect(Collectors.toList());
			list.forEach(a -> a.setHistory(new PetAppointmentHistory(null, a)));
			appointmentRepository.saveAll(list);
		}
	}
}
