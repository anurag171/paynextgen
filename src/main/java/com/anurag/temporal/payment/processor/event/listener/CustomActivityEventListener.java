package com.anurag.temporal.payment.processor.event.listener;


import com.anurag.temporal.payment.processor.constant.ActivityEventEnum;
import com.anurag.temporal.payment.processor.model.mongo.MongoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomActivityEventListener {

    @Autowired
    MongoTemplate mongoTemplate;

    @Async
    @EventListener
    public void handleActivityEvent(MongoEvent event, ActivityEventEnum activityEventEnum) {

        switch (activityEventEnum) {
            case MONGO_EVENT -> mongoAction(event);
        }

    }

    public void mongoAction(MongoEvent event) {
        log.info("Received event {}", event);
        mongoTemplate.save(event);
    }
}

