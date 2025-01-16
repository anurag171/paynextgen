package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.constant.ActivityEventEnum;
import com.anurag.temporal.payment.processor.event.PaymentEvent;
import com.anurag.temporal.payment.processor.event.listener.CustomActivityEventListener;
import com.anurag.temporal.payment.processor.event.mongo.MongoEvent;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.SanctionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
public class PaymentFxCalculationActivityImpl implements PaymentFxCalculationActivity {

    private final CustomActivityEventListener customActivityEventListener;

    public PaymentFxCalculationActivityImpl(CustomActivityEventListener customActivityEventListener) {
        this.customActivityEventListener = customActivityEventListener;
    }

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject calculateFx(PaymentObject paymentObject) {
        ThreadContext.getContext().put("workflowid", "Payment_" + paymentObject.getId());
        log.info("Inside the sanction activity");
        var fxRequestMsg = generateFxRequest(paymentObject.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(fxRequestMsg, headers);
        ResponseEntity<String> received = restTemplate.exchange("http://localhost:9999/fx", HttpMethod.POST, entity, String.class);
        customActivityEventListener.handleActivityEvent(PaymentEvent.builder()
                .activityEventEnum(ActivityEventEnum.MONGO_EVENT)
                .mongoEvent(MongoEvent.builder().workflowId("Payment_" + paymentObject.getId())
                        .message("from payment sanction activity for workflow id Payment_" + paymentObject.getId()).build())
                .build());

        return paymentObject;
    }

    private String generateFxRequest(String message) {

        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(message, Map.class);
        return gson.toJson(Map.of());
    }


}
