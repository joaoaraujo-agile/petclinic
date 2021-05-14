package com.agile.petclinic.queues;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.agile.petclinic.queues.config.PaymentHistoryRabbitMQConfig;

@Component
public class PaymentHistorySender {
	private final RabbitTemplate rabbitTemplate;
	private final PaymentHistoryReceiver receiver;

	public PaymentHistorySender(PaymentHistoryReceiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	public void send(String data) {
		rabbitTemplate.convertAndSend(PaymentHistoryRabbitMQConfig.getTopicExchangeName(), "paymenthistory.post",
				data);
		try {
			receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
