package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.spring.boot.ActivityImpl;

@ActivityImpl
public class PaymentSanctionActivityImpl implements PaymentSanctionActivity{
    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject execute(PaymentObject paymentObject) {
        return sanction(paymentObject);
    }

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject sanction(PaymentObject paymentObject) {
        return paymentObject;
    }
}
