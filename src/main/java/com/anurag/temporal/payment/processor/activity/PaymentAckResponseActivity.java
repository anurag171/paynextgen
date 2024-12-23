package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;

public interface PaymentAckResponseActivity{

    PaymentObject generateAck(PaymentObject paymentObject);

    default PaymentObject execute(PaymentObject paymentObject){
        //implement the use
        return paymentObject;
    }
}
