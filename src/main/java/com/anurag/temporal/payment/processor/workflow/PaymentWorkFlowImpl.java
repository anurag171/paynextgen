package com.anurag.temporal.payment.processor.workflow;

import com.anurag.temporal.payment.processor.activity.PaymentSanctionActivity;
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
import org.jdom2.JDOMException;

import java.io.IOException;
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

    private final ActivityOptions sanctionActivityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(validationActivityRetryOptions).build();

    public final PaymentSanctionActivity sanctionActivity = Workflow
            .newActivityStub(PaymentSanctionActivity.class, sanctionActivityOptions);

    @Override
    public PaymentObject process(PaymentObject paymentObject) throws IOException, JDOMException {
        PaymentObject paymentObject1 = validationActivity.validate(paymentObject);
        sanctionActivity.execute(paymentObject1);
        Workflow.sleep(Duration.ofMinutes(2));
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
    public void processAsynchrousSanctionResponse(String workflowId) {

    }

    /**
     *
     */
    @Override
    public void processAsynchrousFraudResponse(String workflowId) {

    }

    /**
     *
     */
    @Override
    public void byPassHoldWorkflow(String workflowId) {

    }
}
