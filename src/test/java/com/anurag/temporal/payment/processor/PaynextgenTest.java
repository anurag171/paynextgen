package com.anurag.temporal.payment.processor;

import com.anurag.temporal.payment.processor.event.warmup.PaymentWarmUpController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * Unit test for simple App.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile(value = "test")
public class PaynextgenTest
{
    @Autowired
    Environment environment;

    @BeforeEach
    public void setUp(){
        System.setProperty("secret",environment.getProperty("secret"));
        System.setProperty("db.connection",environment.getProperty("db.connection"));
        System.setProperty("enableStub",environment.getProperty("enableStub"));
    }

   @Test
    void contextLoads() {

    }


}
