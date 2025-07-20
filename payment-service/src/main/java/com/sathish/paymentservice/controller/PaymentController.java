package com.sathish.paymentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sathish.commonlibs.dto.PaymentRequest;
import com.sathish.paymentservice.model.Payment;
import com.sathish.paymentservice.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService paymentService;

	@PostMapping
	public ResponseEntity<?> createPayment(@RequestHeader("Idempotency-Key") String idempotencyKey,
			@RequestBody PaymentRequest paymentRequest) {
		if (idempotencyKey == null || idempotencyKey.isEmpty()) {
			return ResponseEntity.badRequest().body("Idempotency-Key header is missing.");
		}
		Payment payment = paymentService.processPayment(paymentRequest, idempotencyKey);
		return ResponseEntity.status(HttpStatus.CREATED).body(payment);
	}
}