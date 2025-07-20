package com.sathish.notificationservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.sathish.commonlibs.events.PaymentCompletedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentCompleteListener {
	private final SimpMessagingTemplate messagingTemplate;

	@RabbitListener(queues = "payment.completed.queue")
	public void onPaymentCompleted(PaymentCompletedEvent event) {
		// Push notification to WebSocket topic
		messagingTemplate.convertAndSend("/topic/payments", event);
	}
}