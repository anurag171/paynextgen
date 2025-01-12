package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.event.listener.CustomActivityEventListener;
import com.anurag.temporal.payment.processor.model.FraudRequest;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.SanctionRequest;
import com.anurag.temporal.payment.processor.service.PaymentUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        return PaymentUtility.handleHttpResponse(received, paymentObject);
    }

    private String generateFraudRequest(String pain001v9) throws IOException {
        log.info("generating fraud check request");
        return PaymentUtility.xmlToJson(pain001v9);
    }
}
