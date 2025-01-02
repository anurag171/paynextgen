package com.anurag.temporal.payment.processor.controller;

import com.anurag.temporal.payment.processor.model.FraudResponse;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.SanctionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface PaymentNextGenController {


    ResponseEntity createPayment(@RequestBody() PaymentObject paymentObject) throws JsonProcessingException;

    ResponseEntity processAsynchronousSanctionResponse(@RequestBody SanctionResponse sanctionResponse);
    ResponseEntity processAsynchronousFraudResponse(@RequestBody FraudResponse fraudResponse);
}
