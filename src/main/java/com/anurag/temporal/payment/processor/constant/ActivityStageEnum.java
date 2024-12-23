package com.anurag.temporal.payment.processor.constant;

import com.anurag.temporal.payment.processor.activity.*;

import java.util.HashMap;
import java.util.Map;

public enum ActivityStageEnum {


    VALIDATION("validation",new PaymentValidationActivityImpl()),
    SANCTION("sanction",new PaymentSanctionActivityImpl()),
    FRAUD("fraud",new PaymentFraudActivityImpl()),
    FUNDCHECK("fundcheck",new PaymentFundCheckActivityImpl()),
    FX("fx",new PaymentFxCalculationActivityImpl()),
    POSTING("posting", new PaymentPostingActivityImpl());


   final static Map<String, Object> map = new HashMap<>();

   static {

        for(ActivityStageEnum ele: ActivityStageEnum.values()){
            map.put(ele.name(), ele.paymentActivty);
        }
          }
    private final Object paymentActivty;
    private final String activity;



    public static Object getPaymentActivity(String activity){
        return map.get(activity);
    }
    
    ActivityStageEnum(String activity, Object paymentActivty) {
        this.activity = activity;
        this.paymentActivty = paymentActivty;
    }
}
