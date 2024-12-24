package com.anurag.temporal.payment.processor;

import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoConfiguration {

    @Value("$")
    private String host;
    private String port;
    private String database;
    private String collection;
    private String username;
    private String password;

    public MongoClient getMongoClient() {

        MongoClient mongoClient = MongoClients.create("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + database) ;
        mongoClient.withWriteConcern(WriteConcern.JOURNALED);
        mongoClient.withReadConcern(ReadConcern.AVAILABLE);
        return mongoClient;
    }

}
