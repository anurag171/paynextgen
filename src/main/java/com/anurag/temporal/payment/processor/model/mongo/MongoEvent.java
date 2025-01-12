package com.anurag.temporal.payment.processor.model.mongo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
public class MongoEvent {
    @Id
    private String id;
    private String message;
    public MongoEvent( String id, String message) {
        this.id = id;
        this.message = message;
    }
}
