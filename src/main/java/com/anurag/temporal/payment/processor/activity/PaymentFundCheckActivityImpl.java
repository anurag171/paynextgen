package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;

public class PaymentFundCheckActivityImpl implements PaymentFundCheckActivity{
    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject execute(PaymentObject paymentObject) {
        return checkFund(paymentObject);
    }


    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject checkFund(PaymentObject paymentObject) {
        return null;
    }
}
