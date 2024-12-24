package com.anurag.temporal.payment.processor.workflow;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface PaymentWorkFlow {

    @WorkflowMethod
    PaymentObject process(PaymentObject paymentObject);

    @SignalMethod
    void processAsynchrousSanctionResponse();

    @SignalMethod
    void processAsynchrousFraudResponse();

    @SignalMethod
    void signalOrderDelivered();
}
