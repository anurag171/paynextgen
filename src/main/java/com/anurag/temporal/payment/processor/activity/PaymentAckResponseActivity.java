package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;

public interface PaymentAckResponseActivity{

    PaymentObject generateAcknowledgement(PaymentObject paymentObject);

}
