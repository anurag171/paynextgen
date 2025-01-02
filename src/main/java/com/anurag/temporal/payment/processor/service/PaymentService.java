package com.anurag.temporal.payment.processor.service;

import com.anurag.temporal.payment.processor.configuration.AppTemporalProperties;
import com.anurag.temporal.payment.processor.model.FraudRequest;
import com.anurag.temporal.payment.processor.model.FraudResponse;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.SanctionResponse;
import com.anurag.temporal.payment.processor.workflow.PaymentWorkFlow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class PaymentService {


	@Autowired
	WorkflowClient workflowClient;

	@Autowired
	AppTemporalProperties appTemporalProperties;


	public PaymentObject startPaymentProcessing(PaymentObject paymentObject) throws IOException, JDOMException {
		PaymentWorkFlow workflow = createWorkFlowConnection(paymentObject);
		return workflow.process(paymentObject);
		//WorkflowClient.start(workflow::process,paymentObject);
	}

	public void processAsynchrousSanctionResponse(SanctionResponse sanctionResponse) {
		log.info("Received signal for workflow id {}",sanctionResponse.getWorkflowid() );
		PaymentWorkFlow workflow = workflowClient.newWorkflowStub(PaymentWorkFlow.class, "Payment_" + sanctionResponse.getWorkflowid());
		 workflow.processAsynchrousSanctionResponse(sanctionResponse);
	}

	public void processAsynchrnousFraudResponse(FraudResponse fraudResponse) {
		PaymentWorkFlow workflow = workflowClient.newWorkflowStub(PaymentWorkFlow.class, "Payment_" + fraudResponse.getWorkflowid());
		workflow.processAsynchrousFraudResponse(fraudResponse);
	}

	public void bypassHoldWorkflow(String workflowId) {
		PaymentWorkFlow workflow = workflowClient.newWorkflowStub(PaymentWorkFlow.class, "Payment_" + workflowId);
		workflow.byPassHoldWorkflow(workflowId);
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