package com.anurag.temporal.payment.processor.configuration;

import com.anurag.temporal.payment.processor.event.listener.PostApplicationReadyEventListener;
import com.anurag.temporal.payment.processor.event.warmup.PaymentWarmUpEnumDto;
import com.anurag.temporal.payment.processor.event.warmup.PaymentWarmUpRequestDto;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.WorkflowExecutionStatus;
import io.temporal.api.workflow.v1.WorkflowExecutionInfo;
import io.temporal.api.workflowservice.v1.DescribeWorkflowExecutionRequest;
import io.temporal.api.workflowservice.v1.DescribeWorkflowExecutionResponse;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class TemporalClientHealthVerification{

    @Autowired
    AppTemporalProperties temporalProperties;

    @Autowired
    PostApplicationReadyEventListener postApplicationReadyEventListener;

    @EventListener(ApplicationReadyEvent.class)
    public void temporalWorkerHealthCheck() throws Exception {
        log.info("Check if temporal worker is successfully created");
        if(temporalProperties.getHealthCheck().equals("Y")){
            WorkflowServiceStubsOptions workflowServiceStubsOptions = WorkflowServiceStubsOptions
                    .newBuilder().setTarget(String.join(":",temporalProperties.getHost(), temporalProperties.getPort())).build();
            WorkflowServiceStubs serviceStubs = WorkflowServiceStubs.newServiceStubs(workflowServiceStubsOptions);
            WorkflowClient client = WorkflowClient.newInstance(serviceStubs);
            WorkflowExecution execution = WorkflowExecution.newBuilder()
                    //.setWorkflowId(workflowId)
                    //.setRunId(runId)
                    .build();

            DescribeWorkflowExecutionRequest request = DescribeWorkflowExecutionRequest.newBuilder()
                    .setNamespace("default")
                    .setExecution(execution)
                    .build();

            DescribeWorkflowExecutionResponse response = serviceStubs.blockingStub().describeWorkflowExecution(request);
            WorkflowExecutionInfo executionInfo = response.getWorkflowExecutionInfo();
            WorkflowExecutionStatus status = executionInfo.getStatus();

            switch (status){
                case WorkflowExecutionStatus.WORKFLOW_EXECUTION_STATUS_RUNNING ->log.info("Workflow is running");
                case WORKFLOW_EXECUTION_STATUS_FAILED -> log.info("Workflow failed");
                case WORKFLOW_EXECUTION_STATUS_CANCELED -> log.info("Workflow was canceled");
                case WORKFLOW_EXECUTION_STATUS_COMPLETED -> log.info("Workflow is completed");
                case WORKFLOW_EXECUTION_STATUS_TERMINATED -> log.info("Workflow is terminated");
                case WORKFLOW_EXECUTION_STATUS_TIMED_OUT -> log.info("Workflow timed out");
                case UNRECOGNIZED -> log.info("Workflow status unrecognized");
                default -> log.info("Could not determine anything");
            }
        }
        postApplicationReadyEventListener.handlePostApplicationReadyEvent(warmUpSampleMessage() );
        }

    private PaymentWarmUpRequestDto warmUpSampleMessage() {
        final PaymentWarmUpRequestDto warmUpRequestDto = new PaymentWarmUpRequestDto();
        warmUpRequestDto.setWarmUpString("warm me up");
        warmUpRequestDto.setWarmUpNumber(15);
        warmUpRequestDto.setWarmUpBigDecimal(BigDecimal.TEN);
        warmUpRequestDto.setWarmUpEnumDto(PaymentWarmUpEnumDto.WARM);

        return warmUpRequestDto;
    }
}