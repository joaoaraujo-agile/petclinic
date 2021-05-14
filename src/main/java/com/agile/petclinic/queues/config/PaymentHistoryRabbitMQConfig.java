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

import com.agile.petclinic.queues.PaymentHistoryReceiver;

@Configuration
public class PaymentHistoryRabbitMQConfig {

	private static final String topicExchangeName = "petclinic-exchange-payment-history";

	static final String queueName = "payment-history";

	@Bean
	Queue queuePaymentHistory() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchangePaymentHistory() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding bindingPaymentHistory(Queue queuePaymentHistory, TopicExchange exchangePaymentHistory) {
		return BindingBuilder.bind(queuePaymentHistory).to(exchangePaymentHistory).with("paymenthistory.#");
	}

	@Bean
	SimpleMessageListenerContainer containerPaymentHistory(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapterPaymentHistory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapterPaymentHistory);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapterPaymentHistory(PaymentHistoryReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static String getTopicExchangeName() {
		return topicExchangeName;
	}
}
