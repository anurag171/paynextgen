package com.anurag.temporal.payment.processor.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "acitivityName")
@Data
public class ActivityObject {
    private boolean validated;
    private int validationCode;
    private List<String> validationFailureList;
}
