package com.anurag.temporal.payment.processor.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusContainer {
    private boolean isPaymentValidated;
}
