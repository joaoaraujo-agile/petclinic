package com.agile.petclinic.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.entities.Payment;
import com.agile.petclinic.entities.PaymentHistory;
import com.agile.petclinic.queues.PaymentHistorySender;
import com.agile.petclinic.repositories.PaymentHistoryRepository;
import com.agile.petclinic.repositories.PaymentRepository;
import com.agile.petclinic.services.exceptions.ResourceNotFoundException;

@Service
public class PaymentHistoryService {

	@Autowired
	private PaymentHistoryRepository repository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentHistorySender sender;

	public List<PaymentHistory> findAll() {
		return repository.findAll();
	}

	public PaymentHistory findById(Long id) {
		Optional<PaymentHistory> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public PaymentHistory insert(Long appointmentId) {
		try {			
			Payment obj = paymentRepository.findById(appointmentId).orElseThrow(() -> new ResourceNotFoundException(appointmentId));
			obj.setHistory(new PaymentHistory(null, obj));			
			paymentRepository.save(obj);
			return obj.getHistory();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public PaymentHistory update(Long id) {
		try {
			PaymentHistory entity = repository.getOne(id);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public void send(Payment obj) {
		sender.send(obj.getId().toString());
	}

	public void verifyHistory() {
		if (repository.count() != paymentRepository.count()) {
			List<Payment> list = paymentRepository.findAll().stream().filter(p -> p.getHistory() == null).collect(Collectors.toList());
			list.forEach(a -> a.setHistory(new PaymentHistory(null, a)));
			paymentRepository.saveAll(list);
		}
	}
}
