package com.sathish.mockbankservice.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bank")
@Slf4j
@CrossOrigin
public class BankController {

	@PostMapping("/process")
	public ResponseEntity<String> processPayment(@RequestBody Object paymentEvent) throws InterruptedException {
		log.info("Mock Bank Received payment request: {}", paymentEvent);

		// Simulate network delay
		Thread.sleep(ThreadLocalRandom.current().nextInt(50, 300));

		// Simulate random failures
		if (ThreadLocalRandom.current().nextBoolean()) {
			log.info("--> Bank approved payment.");
			return ResponseEntity.ok("Payment Approved");
		} else {
			log.warn("--> Bank declined payment (simulating failure).");
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Bank Service Unavailable");
		}
	}
}