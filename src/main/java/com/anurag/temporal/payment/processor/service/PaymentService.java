package com.anurag.temporal.payment.processor.service;

import com.anurag.temporal.payment.processor.configuration.AppTemporalProperties;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.workflow.PaymentWorkFlow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {


	@Autowired
	WorkflowClient workflowClient;

	@Autowired
	AppTemporalProperties appTemporalProperties;


	public PaymentObject startPaymentProcessing(PaymentObject paymentObject) throws JsonProcessingException {
		PaymentWorkFlow workflow = createWorkFlowConnection(paymentObject);
		return workflow.process(paymentObject);
		//WorkflowClient.start(workflow::process,paymentObject);
	}

	public void processAsynchrousSanctionResponse(String workflowId) {
		PaymentWorkFlow workflow = workflowClient.newWorkflowStub(PaymentWorkFlow.class, "Payment_" + workflowId);
		 workflow.processAsynchrousSanctionResponse();
	}

	public void processAsynchrnousFraudResponse(String workflowId) {
		PaymentWorkFlow workflow = workflowClient.newWorkflowStub(PaymentWorkFlow.class, "Payment_" + workflowId);
		workflow.processAsynchrousFraudResponse();
	}

	public void makeOrderDelivered(String workflowId) {
		PaymentWorkFlow workflow = workflowClient.newWorkflowStub(PaymentWorkFlow.class, "Payment_" + workflowId);
		workflow.signalOrderDelivered();
	}

	public PaymentWorkFlow createWorkFlowConnection(PaymentObject paymentObject) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		var deserialized = objectMapper.writeValueAsString(paymentObject);

		WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(appTemporalProperties.getQueue())
				.setWorkflowId("Payment_" + paymentObject.getId())
				.build();
		return workflowClient.newWorkflowStub(PaymentWorkFlow.class, options);
	}

}