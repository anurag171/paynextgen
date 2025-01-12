package com.anurag.temporal.payment.processor.event.listener;


import com.anurag.temporal.payment.processor.event.PaymentEvent;
import com.anurag.temporal.payment.processor.event.mongo.MongoEvent;
import com.anurag.temporal.payment.processor.event.mongo.sequence.DatabaseSequence;
import com.anurag.temporal.payment.processor.event.mongo.sequence.SequenceGeneratorService;
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

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Async
    @EventListener
    public void handleActivityEvent(PaymentEvent event) {

        switch (event.getActivityEventEnum()) {
            case MONGO_EVENT -> mongoAction(event.getMongoEvent());
        }

    }

    private void mongoAction(MongoEvent event) {
        log.info("Received event {}", event);
        event.setId(String.valueOf(sequenceGeneratorService.generateSequence("payment_sequence")));
        mongoTemplate.save(event);
    }
}

