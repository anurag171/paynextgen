package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.event.listener.CustomActivityEventListener;
import com.anurag.temporal.payment.processor.model.PaymentObject;

public class PaymentFxCalculationActivityImpl implements PaymentFxCalculationActivity{

    private final CustomActivityEventListener customActivityEventListener;

    public PaymentFxCalculationActivityImpl(CustomActivityEventListener customActivityEventListener) {
        this.customActivityEventListener = customActivityEventListener;
    }

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject calculateFx(PaymentObject paymentObject) {
        return paymentObject;
    }


}
