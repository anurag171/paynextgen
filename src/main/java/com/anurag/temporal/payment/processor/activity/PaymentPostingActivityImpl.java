package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.spring.boot.ActivityImpl;

@ActivityImpl
public class PaymentPostingActivityImpl implements PaymentPostingActivity{

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject createPostings(PaymentObject paymentObject) {
        return paymentObject;
    }
}
