package com.anurag.temporal.payment.processor.event.listener;


import com.anurag.temporal.payment.processor.event.warmup.PaymentWarmUpRequestDto;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
public class StubEventListener {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ObjectMapper objectMapper;

    @EventListener
    public void handleStubEvent(PaymentObject inputMessage){
        final String serverPort = environment.getProperty("local.server.port");
        final String baseUrl = "http://localhost:" + serverPort;
        final String warmUpEndpoint = baseUrl + "/v1/start";

        log.info("Sending REST request to force initialization of Jackson...");

        final String response = webClientBuilder.build().post()
                .uri(warmUpEndpoint)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(inputMessage), PaymentObject.class)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(60))
                .block();
        if(inputMessage.getWarmUp().equals("Y"))
            log.info("...warm up done, response received: {} " , response);
        else
            log.info("...response received: {} " , response);

    }
}
