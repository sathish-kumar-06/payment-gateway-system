package com.sathish.paymentservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sathish.paymentservice.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
	Optional<Payment> findByIdempotencyKey(String idempotencyKey);
}