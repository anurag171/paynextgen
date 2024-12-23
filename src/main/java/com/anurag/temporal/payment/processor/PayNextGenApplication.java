package com.anurag.temporal.payment.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
@SpringBootApplication
@Slf4j
public class PayNextGenApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(PayNextGenApplication.class,args);
    }


}

