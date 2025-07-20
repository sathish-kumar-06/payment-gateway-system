package com.sathish.paymentservice.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PAYMENT")
public class Payment {
	@Id
	private String id;
	private String userId;
	private BigDecimal amount;
	private String currency;
	private String status; // PENDING, SUCCESSFUL, FAILED
	private String idempotencyKey;
	@CreationTimestamp
	private Instant createdAt;
}