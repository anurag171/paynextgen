package com.anurag.temporal.payment.processor.model;

public class SanctionRequest {

    private String id;
    private String message;

    public SanctionRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
