package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.activity.ActivityInterface;

import java.io.IOException;

@ActivityInterface
public interface PaymentSanctionActivity {

    PaymentObject sanction(PaymentObject paymentObject) throws IOException;

    default PaymentObject execute(PaymentObject paymentObject){
        //implement the use
        return paymentObject;
    }

}
