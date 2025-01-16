package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.event.listener.CustomActivityEventListener;
import com.anurag.temporal.payment.processor.model.PaymentObject;

public class PaymentAckResponseActivityImpl implements PaymentAckResponseActivity{

    private final CustomActivityEventListener customActivityEventListener;

    public PaymentAckResponseActivityImpl(CustomActivityEventListener customActivityEventListener) {
        this.customActivityEventListener = customActivityEventListener;
    }

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject generateAcknowledgement(PaymentObject paymentObject) {
        return paymentObject;
    }
}
