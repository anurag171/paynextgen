package com.anurag.temporal.payment.processor.workflow;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.jdom2.JDOMException;

import java.io.IOException;

@WorkflowInterface
public interface PaymentWorkFlow {

    @WorkflowMethod
    PaymentObject process(PaymentObject paymentObject) throws IOException, JDOMException;

    @SignalMethod
    void processAsynchrousSanctionResponse(String workflowId);

    @SignalMethod
    void processAsynchrousFraudResponse(String workflowId);

    @SignalMethod
    void byPassHoldWorkflow(String workflowId);
}
