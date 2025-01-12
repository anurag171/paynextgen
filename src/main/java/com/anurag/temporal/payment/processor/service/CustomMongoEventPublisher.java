package com.anurag.temporal.payment.processor.service;

import com.anurag.temporal.payment.processor.model.mongo.MongoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomMongoEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomMongoEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(MongoEvent message) {

        applicationEventPublisher.publishEvent(message);
    }
}
