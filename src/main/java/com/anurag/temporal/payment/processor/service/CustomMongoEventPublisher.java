package com.anurag.temporal.payment.processor.service;

import com.anurag.temporal.payment.processor.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomMongoEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomMongoEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(PaymentEvent paymentEvent) {
        log.info("Publish event {}",paymentEvent);
        applicationEventPublisher.publishEvent(paymentEvent);
    }
}
