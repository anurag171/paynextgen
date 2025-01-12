package com.anurag.temporal.payment.processor.event.mongo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@Builder
public class MongoEvent {
    @Id
    private String id;

    private String workflowId;

    private String message;
}
