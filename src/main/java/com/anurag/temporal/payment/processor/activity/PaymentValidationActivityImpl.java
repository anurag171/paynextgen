package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.constant.ActivityStageEnum;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.PaymentValidationActivityObject;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.SAXBuilder;
import org.springframework.web.client.RestTemplate;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class PaymentValidationActivityImpl implements PaymentValidationActivity{


    @Override
    public PaymentObject validate(PaymentObject paymentObject) throws IOException, JDOMException {
        log.info("Received message {} in {}",paymentObject.toString(),this.getClass());
        RestTemplate restTemplate = new RestTemplate();
        String valid = restTemplate.getForObject(String.format("http://localhost:9999/dupcheck?messageId=%s",paymentObject.getId()),String.class);
        List<String> list = new ArrayList<>();
        if(Boolean.parseBoolean(valid)){
            var message = new String(Base64.getDecoder().decode(paymentObject.getMessage()))  ;
            if(!message.contains("<") && !valideXml(message)){
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

    public boolean valideXml(String xml) throws JDOMException, IOException
    {
        SAXBuilder builder = new SAXBuilder();
        try{
            Document doc = builder.build(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JDOMParseException ex) {
            return false;
        }
        return true;
    }
}
