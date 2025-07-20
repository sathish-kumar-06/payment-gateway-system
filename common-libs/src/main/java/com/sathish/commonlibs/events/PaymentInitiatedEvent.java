package com.sathish.commonlibs.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiatedEvent implements Serializable


{
	private String paymentId;
	private String idempotencyKey;
}
