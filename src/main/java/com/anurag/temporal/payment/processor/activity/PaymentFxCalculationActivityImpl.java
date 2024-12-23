package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;

public class PaymentFxCalculationActivityImpl implements PaymentFxCalculationActivity{

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject calculateFx(PaymentObject paymentObject) {
        return paymentObject;
    }


}
