package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.constant.ActivityStageEnum;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.PaymentValidationActivityObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class PaymentValidationActivityImpl implements PaymentValidationActivity{


    @Override
    public PaymentObject validate(PaymentObject paymentObject) {
        log.info("Received message {} in {}",paymentObject.toString(),this.getClass());
        RestTemplate restTemplate = new RestTemplate();
        String valid = restTemplate.getForObject(String.format("http://localhost:9999/dupcheck?messageId=%s",paymentObject.getId()),String.class);
          paymentObject.getActivityObjectMap().put(ActivityStageEnum.VALIDATION.name(),
                 PaymentValidationActivityObject.builder().validated(Boolean.parseBoolean(valid))
                         .validationFailureList(!Boolean.parseBoolean(valid) ?List.of(String.format("%s is duplicate",paymentObject.getId())):List.of()).build());
          return paymentObject;
    }
}
