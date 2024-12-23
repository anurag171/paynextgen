package com.anurag.temporal.payment.processor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentValidationActivityObject extends ActivityObject{

     private boolean validated;
     private int validationCode;
     private List<String> validationFailureList;
}
