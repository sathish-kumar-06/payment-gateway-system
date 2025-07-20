package com.sathish.processorservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sathish.commonlibs.events.PaymentInitiatedEvent;
import com.sathish.processorserviceservice.PaymentProcessor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentListener {
	
	@Autowired
	private final PaymentProcessor paymentProcessor;

	@RabbitListener(queues = "payment.initiated.queue")
	public void onPaymentInitiated(PaymentInitiatedEvent event) {
		paymentProcessor.processPayment(event);
	}
}
