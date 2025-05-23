package com.anurag.temporal.payment.processor.controller;

import com.anurag.temporal.payment.processor.model.*;
import com.anurag.temporal.payment.processor.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Slf4j
public class PaymentNextGenControllerImpl implements PaymentNextGenController{

    @Autowired
    PaymentService paymentService;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * @param paymentObject
     * @return
     */
    @Override
    @PostMapping(path = "/start",consumes = "application/json")
    public ResponseEntity createPayment(PaymentObject paymentObject) throws JsonProcessingException {
        PaymentObject response;
        try{
            response = paymentService.startPaymentProcessing(paymentObject);
        }catch (Exception ex){
            log.error("Error while proceesing payment []",ex.fillInStackTrace());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * @param sanctionResponse
     * @return
     */
    @Override
    @PostMapping(path = "/sanctions")
    public ResponseEntity<String> processAsynchronousSanctionResponse(@RequestBody SanctionResponse sanctionResponse) {
        log.info("Received sanction asynchronous response {}",sanctionResponse);
        PaymentStatusContainer paymentStatusContainer = applicationContext.getBean(PaymentStatusContainer.class);
        paymentService.processAsynchrousSanctionResponse(sanctionResponse);
        return ResponseEntity.ok(String.valueOf(paymentStatusContainer.isPaymentValidated()));
    }

    /**
     * @param fraudResponse
     * @return
     */
    @Override
    @PostMapping(path = "/fraud")
    public ResponseEntity<String> processAsynchronousFraudResponse(@RequestBody FraudResponse fraudResponse) {
        log.info("Received fraud asynchronous response {}",fraudResponse);
        PaymentStatusContainer paymentStatusContainer = applicationContext.getBean(PaymentStatusContainer.class);
        paymentService.processAsynchrnousFraudResponse(fraudResponse);
        return ResponseEntity.ok(String.valueOf(paymentStatusContainer.isPaymentValidated()));
    }

    /**
     * @param workflowid
     * @return
     */
    @Override
    @GetMapping
    public ResponseEntity getPaymentStatus(String workflowid) {
        return ResponseEntity.ok(paymentService.getWorkflowStatus(workflowid));
    }
}
