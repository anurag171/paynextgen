package com.anurag.temporal.payment.processor.model;

import lombok.Data;

@Data
public class FraudResponse {
            private String result;

    public FraudResponse(){

    }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public String getWorkflowid() {
                return workflowid;
            }

            public void setWorkflowid(String workflowid) {
                this.workflowid = workflowid;
            }

            private String workflowid;
            private String response;

            public FraudResponse(String request) {
                this.response  =  request;
            }

            public String getResponse() {
                return response;
            }
        }