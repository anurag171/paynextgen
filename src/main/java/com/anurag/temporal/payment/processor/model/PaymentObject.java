package com.anurag.temporal.payment.processor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentObject {

    private String id;
    private String message;
    private String responseString;
    private LocalDateTime receivedTime;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private BigDecimal rate;
    private String debitCcy;
    private String creditCcy;
    private Map<String,Object> map;
    private Map<String,ActivityObject> activityObjectMap= new HashMap<>();
    private PaymentStatusContainer paymentStatusContainer;
    private String warmUp;

}
