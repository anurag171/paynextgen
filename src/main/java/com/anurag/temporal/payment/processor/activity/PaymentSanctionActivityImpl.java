package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.service.PaymentUtility;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@ActivityImpl
public class PaymentSanctionActivityImpl implements PaymentSanctionActivity{
    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject execute(PaymentObject paymentObject) {
        try {
            return sanction(paymentObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param paymentObject
     * @return
     */
    @Override
    public PaymentObject sanction(PaymentObject paymentObject) throws IOException {
       var sanctionRequest =  generateSanctionRequest(paymentObject.getMessage());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(sanctionRequest,headers);
        ResponseEntity<Boolean> received = restTemplate.exchange("http://localhost:9999/sanction", HttpMethod.POST,entity,Boolean.class);

        return paymentObject;
    }

    private String generateSanctionRequest(String pain001v9) throws IOException {
            return PaymentUtility.xmlToJson(pain001v9);
    }
}
