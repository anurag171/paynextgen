package com.anurag.temporal.payment.processor.controller;

import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.PaymentStatusContainer;
import com.anurag.temporal.payment.processor.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param workflowId
     * @return
     */
    @Override
    @PostMapping
    public ResponseEntity<String> processAsynchronousSanctionResponse(String workflowId) {
        PaymentStatusContainer paymentStatusContainer = applicationContext.getBean(PaymentStatusContainer.class);
        paymentService.processAsynchrousSanctionResponse(workflowId);
        return ResponseEntity.ok(String.valueOf(paymentStatusContainer.isPaymentValidated()));
    }

    /**
     * @param workflowId
     * @return
     */
    @Override
    @PostMapping
    public ResponseEntity<String> processAsynchronousFraudResponse(String workflowId) {
        PaymentStatusContainer paymentStatusContainer = applicationContext.getBean(PaymentStatusContainer.class);
        paymentService.processAsynchrnousFraudResponse(workflowId);
        return ResponseEntity.ok(String.valueOf(paymentStatusContainer.isPaymentValidated()));
    }
}
