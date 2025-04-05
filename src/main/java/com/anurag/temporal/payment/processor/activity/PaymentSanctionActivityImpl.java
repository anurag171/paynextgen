package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.constant.ActivityEventEnum;
import com.anurag.temporal.payment.processor.event.PaymentEvent;
import com.anurag.temporal.payment.processor.event.listener.CustomActivityEventListener;
import com.anurag.temporal.payment.processor.event.mongo.MongoEvent;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.SanctionRequest;
import com.anurag.temporal.payment.processor.service.PaymentUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.Trace;
import io.temporal.spring.boot.ActivityImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@ActivityImpl
@Slf4j
public class PaymentSanctionActivityImpl implements PaymentSanctionActivity{

    private final CustomActivityEventListener customActivityEventListener;
    public PaymentSanctionActivityImpl(CustomActivityEventListener customActivityEventListener) {
        this.customActivityEventListener = customActivityEventListener;
    }

    /**
     * @param paymentObject
     * @return
     */
    @Trace(metricName = "SanctionRequest",nameTransaction = true,dispatcher = true)
    @Override
    public PaymentObject sanction(PaymentObject paymentObject) throws IOException {
        ThreadContext.getContext().put("workflowid", "Payment_"+paymentObject.getId());
        log.info("Inside the sanction activity");
       var sanctionRequesMsg =  generateSanctionRequest(paymentObject.getMessage());
        SanctionRequest sanctionRequest1 = new SanctionRequest();
        sanctionRequest1.setId(paymentObject.getId());
        sanctionRequest1.setMessage(paymentObject.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(objectMapper.writeValueAsString(sanctionRequest1),headers);
        ResponseEntity<Boolean> received = restTemplate.exchange("http://localhost:9999/sanction", HttpMethod.POST,entity,Boolean.class);
        customActivityEventListener.handleActivityEvent(PaymentEvent.builder()
                .activityEventEnum(ActivityEventEnum.MONGO_EVENT)
                .mongoEvent(MongoEvent.builder().workflowId("Payment_"+paymentObject.getId())
                        .message("from payment sanction activity for workflow id Payment_"+paymentObject.getId()).build())
                .build());

        return paymentObject;
    }

    private String generateSanctionRequest(String pain001v9) throws IOException {
            return PaymentUtility.xmlToJson(pain001v9);
    }
}
