package com.anurag.temporal.payment.processor.workflow;

import com.anurag.temporal.payment.processor.activity.PaymentValidationActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class PaymentWorkflowActivityConstants {

    private static final RetryOptions validationActivityRetryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500).build();
    private static final ActivityOptions validationActivityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(validationActivityRetryOptions).build();
    public static final PaymentValidationActivity validationActivity = Workflow
            .newActivityStub(PaymentValidationActivity.class, validationActivityOptions);
}
