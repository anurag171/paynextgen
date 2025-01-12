package com.anurag.temporal.payment.processor.event;

import com.anurag.temporal.payment.processor.constant.ActivityEventEnum;

import com.anurag.temporal.payment.processor.event.mongo.MongoEvent;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
public class PaymentEvent {

    private ActivityEventEnum activityEventEnum;

    private MongoEvent mongoEvent;

}
