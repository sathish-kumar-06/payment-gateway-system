package com.sathish.commonlibs.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentRequest {

	private String userId;
	private BigDecimal amount;
	private String currency;
	private String paymentMethod;

}
