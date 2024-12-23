package com.anurag.temporal.payment.processor.controller;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface PaymentNextGenController {


    ResponseEntity createPayment(@RequestBody() PaymentObject paymentObject) throws JsonProcessingException;

    ResponseEntity paymentValidated(@RequestParam(name = "workflowId") String workflowId);
}
