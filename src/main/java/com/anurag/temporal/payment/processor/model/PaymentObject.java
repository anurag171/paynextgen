package com.anurag.temporal.payment.processor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private LocalDateTime receivedTime;
    private Map<String,Object> map;
    private Map<String,ActivityObject> activityObjectMap= new HashMap<>();
    private PaymentStatusContainer paymentStatusContainer;

}
