package com.anurag.temporal.payment.processor.configuration;

import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.stereotype.Component;

@Component
public class MongoConfiguration {

    @Autowired
    Environment environment;
    /*@Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private String port;

    @Value("${spring.data.mongodb.database}")
    private String database;
    private String collection;
    @Value("${spring.data.mongodb.username}")
    private String username;
    @Value("${spring.data.mongodb.password}")
    private String password;
*/
    //@Bean
    public MongoClient getMongoClient() {

       // MongoClient mongoClient = MongoClients.create("mongodb+srv://paynextgenuser1:patuu123@mycluster0707.gbqwa.mongodb.net/?retryWrites=true&w=majority&appName=mycluster0707") ;
        MongoClient mongoClient = MongoClients.create(environment.getProperty("mongodb.connection.string")) ;
        mongoClient.withWriteConcern(WriteConcern.JOURNALED);
        mongoClient.withReadConcern(ReadConcern.AVAILABLE);
        return mongoClient;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate myTemp = new MongoTemplate(getMongoClient(), "database");
        myTemp.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        return myTemp;
    }

}
