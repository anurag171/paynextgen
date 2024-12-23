package com.anurag.temporal.payment.processor.workflow;

import com.anurag.temporal.payment.processor.activity.PaymentValidationActivity;
import com.anurag.temporal.payment.processor.constant.ActivityStageEnum;
import com.anurag.temporal.payment.processor.model.ActivityObject;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.PaymentStatusContainer;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;


@WorkflowImpl
@Slf4j
public class PaymentWorkFlowImpl implements PaymentWorkFlow{

    private final RetryOptions validationActivityRetryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(10).build();
    private final ActivityOptions validationActivityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(validationActivityRetryOptions).build();
    public final PaymentValidationActivity validationActivity = Workflow
            .newActivityStub(PaymentValidationActivity.class, validationActivityOptions);

    @Override
    public PaymentObject process(PaymentObject paymentObject) {
        PaymentObject paymentObject1 = validationActivity.validate(paymentObject);
        ActivityObject activityObject = paymentObject1.getActivityObjectMap().get(ActivityStageEnum.VALIDATION.name());
        PaymentStatusContainer paymentStatusContainer = new PaymentStatusContainer();

            paymentStatusContainer.setPaymentValidated(!(!activityObject.isValidated()
                    || !activityObject.getValidationFailureList().isEmpty()));
            paymentObject1.setPaymentStatusContainer(paymentStatusContainer);
        return paymentObject1;
    }

    /**
     *
     */
    @Override
    public void signalPaymentAccepted() {

    }

    /**
     *
     */
    @Override
    public void signalOrderPickedUp() {

    }

    /**
     *
     */
    @Override
    public void signalOrderDelivered() {

    }
}
