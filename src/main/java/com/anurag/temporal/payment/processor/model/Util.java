package com.anurag.temporal.payment.processor.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Util {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        Map<String,ActivityObject> map = new HashMap<>();
        map.put("Validation", PaymentValidationActivityObject.builder().validated(!new ArrayList<>().isEmpty())
                .validationFailureList(new ArrayList<>()).build());
        Map<String,Object> map1 = new HashMap<>();
        map1.put("errorCode","Err01");
        map1.put("errorDescription","Its a dummy error");
        PaymentObject object = PaymentObject.builder()
                .id(UUID.randomUUID().toString()).map(map1)
                .creditAmount(new BigDecimal(700.91).setScale(2, RoundingMode.HALF_DOWN))
                .creditCcy("USD")
                .debitAmount(new BigDecimal(345.78).setScale(2,RoundingMode.HALF_DOWN))
                .debitCcy("GBP")
                .receivedTime(LocalDateTime.now())
                .rate(new BigDecimal(1.67).setScale(4,RoundingMode.HALF_DOWN))
                .warmUp("false").message("payment message")
                .activityObjectMap(map)
                .responseString("failure")
                .paymentStatusContainer(PaymentStatusContainer.builder().isPaymentValidated(true).build())
                .build();
        String json = ow.writeValueAsString(object);
        System.out.println(json);
    }
}
