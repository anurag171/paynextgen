package com.anurag.temporal.payment.processor.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PaymentStatusContainer {
    private boolean isPaymentValidated;
}
