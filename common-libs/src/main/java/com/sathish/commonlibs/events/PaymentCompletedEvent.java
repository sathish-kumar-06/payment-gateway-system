package com.sathish.commonlibs.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompletedEvent implements Serializable {
    private String paymentId;
    private String status;
    private String userId;
    private BigDecimal amount;
}
