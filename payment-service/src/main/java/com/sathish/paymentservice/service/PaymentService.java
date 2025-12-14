package com.sathish.paymentservice.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.sathish.commonlibs.dto.PaymentRequest;
import com.sathish.commonlibs.events.PaymentInitiatedEvent;
import com.sathish.paymentservice.model.Payment;
import com.sathish.paymentservice.repository.PaymentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public Payment processPayment(PaymentRequest request, String idempotencyKey) {
        Optional<Payment> existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey);
        if (existingPayment.isPresent()) {
            return existingPayment.get();
        }

        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus("PENDING");
        payment.setIdempotencyKey(idempotencyKey);
        
        Payment savedPayment = paymentRepository.save(payment);

        // Publish event to be processed by another service
        PaymentInitiatedEvent event = new PaymentInitiatedEvent(savedPayment.getId(), idempotencyKey);
        rabbitTemplate.convertAndSend("payment-exchange", "payment.initiated", event);

        return savedPayment;
    }

    @Transactional
    public void updatePaymentStatus(String paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        paymentRepository.save(payment);
    }
}