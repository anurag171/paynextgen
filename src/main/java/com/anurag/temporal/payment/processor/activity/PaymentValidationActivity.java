package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.activity.ActivityInterface;
import org.jdom2.JDOMException;

import java.io.IOException;

@ActivityInterface
public interface PaymentValidationActivity {

    PaymentObject validate(PaymentObject paymentObject) throws IOException, JDOMException;

}
