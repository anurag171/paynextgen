package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.constant.ActivityEventEnum;
import com.anurag.temporal.payment.processor.event.PaymentEvent;
import com.anurag.temporal.payment.processor.event.listener.CustomActivityEventListener;
import com.anurag.temporal.payment.processor.event.mongo.MongoEvent;
import com.anurag.temporal.payment.processor.model.FraudRequest;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.SanctionRequest;
import com.anurag.temporal.payment.processor.service.PaymentUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.Trace;
import io.temporal.spring.boot.ActivityImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@ActivityImpl
@Slf4j
public class PaymentFraudActivityImpl implements PaymentFraudActivity {

    private final CustomActivityEventListener customActivityEventListener;

    public PaymentFraudActivityImpl(CustomActivityEventListener customActivityEventListener) {
        this.customActivityEventListener = customActivityEventListener;
    }

    /**
     * @param paymentObject
     * @return
     */
    @Trace(metricName = "FraudRequest",nameTransaction = true,dispatcher = true)
    @Override
    public PaymentObject fraudCheck(PaymentObject paymentObject) throws IOException {
        log.info("Inside fraud check request activity");
        ThreadContext.getContext().put("workflowid", "Payment_" + paymentObject.getId());
        var fraudRequest1 = generateFraudRequest(paymentObject.getMessage());
        FraudRequest fraudRequest = new FraudRequest();
        fraudRequest.setId(paymentObject.getId());
        fraudRequest.setMessage(fraudRequest1);
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(objectMapper.writeValueAsString(fraudRequest), headers);
        log.info("Calling fraud check request endpoint");
        ResponseEntity<Boolean> received = restTemplate.exchange("http://localhost:9999/fraud", HttpMethod.POST, entity, Boolean.class);
        customActivityEventListener.handleActivityEvent(PaymentEvent.builder()
                .activityEventEnum(ActivityEventEnum.MONGO_EVENT)
                .mongoEvent(MongoEvent.builder().workflowId("Payment_"+paymentObject.getId())
                        .message("from payment fraud activity for workflow id Payment_"+paymentObject.getId()).build())
                .build());

        return PaymentUtility.handleHttpResponse(received, paymentObject);
    }

    private String generateFraudRequest(String pain001v9) throws IOException {
        log.info("generating fraud check request");
        return PaymentUtility.xmlToJson(pain001v9);
    }
}
