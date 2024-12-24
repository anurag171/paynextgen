package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.constant.ActivityStageEnum;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.PaymentValidationActivityObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class PaymentValidationActivityImpl implements PaymentValidationActivity{


    @Override
    public PaymentObject validate(PaymentObject paymentObject) {
        log.info("Received message {} in {}",paymentObject.toString(),this.getClass());
        RestTemplate restTemplate = new RestTemplate();
        String valid = restTemplate.getForObject(String.format("http://localhost:9999/dupcheck?messageId=%s",paymentObject.getId()),String.class);
        List<String> list = new ArrayList<>();
        if(Boolean.parseBoolean(valid)){
            var message = new String(Base64.getDecoder().decode(paymentObject.getMessage()))  ;
            if(message.contains("<")){
                list.add(String.format("Improper message []",message));
            }
        }else {
            list.add(String.format("%s is duplicate",paymentObject.getId()));
        }

          paymentObject.getActivityObjectMap().put(ActivityStageEnum.VALIDATION.name(),
                 PaymentValidationActivityObject.builder().validated(list.size()>0)
                         .validationFailureList(list).build());
          return paymentObject;
    }
}
