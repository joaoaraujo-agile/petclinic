package com.agile.petclinic.queues;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agile.petclinic.services.PaymentHistoryService;

@Component
public class PaymentHistoryReceiver {

	private CountDownLatch latch = new CountDownLatch(1);
	
	@Autowired
	private PaymentHistoryService service;

	public void receiveMessage(String id) {
		latch.countDown();
		try {			
			service.insert(Long.parseLong(id));
		} catch (RuntimeException e) {
			e.getMessage();
		}
	}

	public CountDownLatch getLatch() {
		return latch;
	}
}
