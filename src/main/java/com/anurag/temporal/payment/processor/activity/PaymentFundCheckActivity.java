package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface PaymentFundCheckActivity{

    PaymentObject checkFund(PaymentObject paymentObject);

    default PaymentObject execute(PaymentObject paymentObject){
        //implement the use
        return paymentObject;
    }
}
