package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface PaymentValidationActivity {

    public PaymentObject validate(PaymentObject paymentObject);

}
