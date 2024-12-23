package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;

public class PaymentAckResponseActivityImpl implements PaymentAckResponseActivity{
    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject execute(PaymentObject paymentObject) {
        return generateAck(paymentObject);
    }

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject generateAck(PaymentObject paymentObject) {
        return paymentObject;
    }
}
