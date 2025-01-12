package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.event.listener.CustomActivityEventListener;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.spring.boot.ActivityImpl;

@ActivityImpl
public class PaymentPostingActivityImpl implements PaymentPostingActivity{

    private final CustomActivityEventListener customActivityEventListener;

    public PaymentPostingActivityImpl(CustomActivityEventListener customActivityEventListener) {
        this.customActivityEventListener = customActivityEventListener;
    }

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject createPostings(PaymentObject paymentObject) {
        return paymentObject;
    }
}
