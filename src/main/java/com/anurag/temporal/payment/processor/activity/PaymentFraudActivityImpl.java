package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.spring.boot.ActivityImpl;

@ActivityImpl
public class PaymentFraudActivityImpl implements PaymentFraudActivity{
    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject execute(PaymentObject paymentObject) {
        return fraudCheck(paymentObject);
    }

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject fraudCheck(PaymentObject paymentObject) {
        return paymentObject;
    }
}
