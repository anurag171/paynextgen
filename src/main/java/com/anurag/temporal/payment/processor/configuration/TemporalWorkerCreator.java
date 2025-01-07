package com.anurag.temporal.payment.processor.configuration;

import com.anurag.temporal.payment.processor.activity.*;
import com.anurag.temporal.payment.processor.workflow.PaymentWorkFlowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TemporalWorkerCreator implements ApplicationRunner {

    @Autowired
    AppTemporalProperties temporalProperties;

    @Autowired
    ApplicationContext appContext;

    /**
     * @param args *
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("Created temporal client here");
        log.info("Starting with below properties [{}] ", temporalProperties.toString());
        if (temporalProperties.getAppCreator().equals("Y") || temporalProperties.getRemoteStub().equals("N")) {

            WorkerFactory factory = appContext.getBean(WorkerFactory.class);
            PaymentValidationActivity paymentValidationActivity = appContext.getBean(PaymentValidationActivity.class);
            PaymentPostingActivity paymentDebitCreditActivity = appContext.getBean(PaymentPostingActivity.class);
            PaymentFraudActivity paymentFraudActivity = appContext.getBean(PaymentFraudActivity.class);
            PaymentSanctionActivity paymentSanctionActivity = appContext.getBean(PaymentSanctionActivity.class);
            Worker worker = factory.newWorker(temporalProperties.getQueue());
            worker.registerWorkflowImplementationTypes(PaymentWorkFlowImpl.class);
            worker.registerActivitiesImplementations(paymentValidationActivity, paymentSanctionActivity, paymentFraudActivity, paymentDebitCreditActivity);
            factory.start();


        }
    }
}
