package com.sathish.processorserviceservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sathish.commonlibs.events.PaymentCompletedEvent;
import com.sathish.commonlibs.events.PaymentInitiatedEvent;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentProcessor {
	
	@Autowired
	private final RestTemplate restTemplate;
	@Autowired
	private final RabbitTemplate rabbitTemplate;

	// Inject the URL from application.properties/yml
    @Value("${MOCK_BANK_URL}")
    private String mockBankUrl;

    @CircuitBreaker(name = "mockBankService", fallbackMethod = "handleBankServiceFailure")
    public void processPayment(PaymentInitiatedEvent event) {
        log.info("Processing payment: {}", event.getPaymentId());

        // Use the configured URL variable instead of a hard-coded string
        ResponseEntity<String> response = restTemplate.postForEntity(
            mockBankUrl,
            event,
            String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Payment successful for {}", event.getPaymentId());
            publishCompletionEvent(event.getPaymentId(), "SUCCESSFUL");
        } else {
            log.error("Payment failed at bank for {}", event.getPaymentId());
            publishCompletionEvent(event.getPaymentId(), "FAILED");
        }
    }

    public void handleBankServiceFailure(PaymentInitiatedEvent event, Throwable t) {
        log.error("Bank service is unavailable for payment {}. Error: {}", event.getPaymentId(), t.getMessage());
        publishCompletionEvent(event.getPaymentId(), "FAILED");
    }

    private void publishCompletionEvent(String paymentId, String status) {
        PaymentCompletedEvent completedEvent = new PaymentCompletedEvent(paymentId, status, null, null);
        rabbitTemplate.convertAndSend("payment-exchange", "payment.completed", completedEvent);
    }
}