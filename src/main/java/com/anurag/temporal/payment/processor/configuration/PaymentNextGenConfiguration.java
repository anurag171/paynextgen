package com.anurag.temporal.payment.processor.configuration;

import com.anurag.temporal.payment.processor.activity.*;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.RpcRetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@Configuration
public class PaymentNextGenConfiguration {

    @Bean
    public AppTemporalProperties temporalProperties(){
        return new AppTemporalProperties();
    }

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs
                .newInstance(WorkflowServiceStubsOptions.newBuilder()
                            .setTarget(String.join(":",temporalProperties().getHost(), temporalProperties().getPort()))
                            .setRpcTimeout(Duration.ofSeconds(60)).setRpcLongPollTimeout(Duration.ofSeconds(60))
                            .setRpcRetryOptions(RpcRetryOptions.newBuilder()
                                                                .setBackoffCoefficient(2)
                                                                .setInitialInterval(Duration.ofSeconds(2))
                                                                .setMaximumAttempts(100)
                                                                .setMaximumInterval(Duration.ofSeconds(2)).build())
                            .build());
    }

    @Bean
    public WorkflowClient workflowClient() {
        return WorkflowClient.newInstance(workflowServiceStubs(),
                WorkflowClientOptions.newBuilder().setNamespace(temporalProperties().getNamespace()).build());
    }

    @Bean
    @Primary
    public WorkerFactory workerFactory() {
        return WorkerFactory.newInstance(workflowClient());
    }

    @Bean
    public PaymentValidationActivityImpl paymentValidationActivity() {
        return new PaymentValidationActivityImpl();
    }

    @Bean
    public PaymentPostingActivityImpl postingActivity() {
        return new PaymentPostingActivityImpl();
    }

    @Bean
    public PaymentFxCalculationActivityImpl fxCalculationActivity() {
        return new PaymentFxCalculationActivityImpl();
    }

    @Bean
    public PaymentSanctionActivityImpl paymentSanctionActivity() {
        return new PaymentSanctionActivityImpl();
    }

    @Bean
    public PaymentFraudActivityImpl paymentFraudActivity() {
        return new PaymentFraudActivityImpl();
    }
}
