package com.agile.petclinic.queues.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agile.petclinic.queues.PetAppointmentHistoryReceiver;

@Configuration
public class PetAppointmentHistoryRabbitMQConfig {

	private static final String topicExchangeName = "petclinic-exchange-pet-history";

	static final String queueName = "pet-history";

	@Bean
	Queue queuePetAppointmentHistory() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchangePetAppointmentHistory() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding bindingPetAppointmentHistory(Queue queuePetAppointmentHistory, TopicExchange exchangePetAppointmentHistory) {
		return BindingBuilder.bind(queuePetAppointmentHistory).to(exchangePetAppointmentHistory).with("pethistory.#");
	}

	@Bean
	SimpleMessageListenerContainer containerPetAppointmentHistory(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapterPetAppointmentHistory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapterPetAppointmentHistory);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapterPetAppointmentHistory(PetAppointmentHistoryReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static String getTopicExchangeName() {
		return topicExchangeName;
	}
}
