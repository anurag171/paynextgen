package com.anurag.temporal.payment.processor.stub;

import com.anurag.temporal.payment.processor.event.listener.StubEventListener;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.UUID;

@ConditionalOnProperty(name = "stub.enabled",havingValue = "Y",matchIfMissing = false)
@Configuration
@EnableScheduling
public class InputStub {

    @Autowired
    StubEventListener stubEventListener;

    @Scheduled(fixedRate = 60000L)
    public void inputMessage(){
        PaymentObject paymentObject = new PaymentObject();
        paymentObject.setId(UUID.randomUUID().toString());
        paymentObject.setMessage("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiID8+IDxEb2N1bWVudCB4bWxucz0idXJuOmlzbzpzdGQ6aXNvOjIwMDIyOnRlY2g6eHNkOnBhaW4uMDAxLjAwMS4wMyI+IDxDc3RtckNkdFRyZkluaXRuPiA8R3JwSGRyPiA8TXNnSWQ+TWVzc2FnZS1JZDwvTXNnSWQ+IDxDcmVEdFRtPjIwMjQtMDUtMTBUMTY6MTA6MDIuMDE3KzAwOjAwPC9DcmVEdFRtPiA8TmJPZlR4cz4xPC9OYk9mVHhzPiA8Q3RybFN1bT41MTAuMjQ8L0N0cmxTdW0+IDxJbml0Z1B0eT4gPElkPiA8T3JnSWQ+IDxPdGhyPiA8SWQ+Q2xpZW50LUlkPC9JZD4gPC9PdGhyPiA8L09yZ0lkPiA8L0lkPiA8L0luaXRnUHR5PiA8L0dycEhkcj4gPFBtdEluZj4gPFBtdEluZklkPkJhdGNoLUlkPC9QbXRJbmZJZD4gPFBtdE10ZD5UUkY8L1BtdE10ZD4gPFBtdFRwSW5mPiA8TGNsSW5zdHJtPiA8Q2Q+U2VjLWNvZGU8L0NkPiA8L0xjbEluc3RybT4gPEN0Z3lQdXJwPiA8UHJ0cnk+QklMTFBZTUVOVDwvUHJ0cnk+IDwvQ3RneVB1cnA+IDwvUG10VHBJbmY+IDxSZXFkRXhjdG5EdD5ZWVlZLU1NLUREPC9SZXFkRXhjdG5EdD4gPERidHI+IDxObT5EZWJ0b3IgQWNjb3VudCBIb2xkZXIgTmFtZTwvTm0+IDxJZD4gPE9yZ0lkPiA8T3Rocj4gPElkPkRlYnRvciBJZDwvSWQ+IDwvT3Rocj4gPC9PcmdJZD4gPC9JZD4gPC9EYnRyPiA8RGJ0ckFjY3Q+IDxJZD4gPE90aHI+IDxJZD5EZWJ0b3IgQWNjb3VudCBJZDwvSWQ+IDwvT3Rocj4gPC9JZD4gPC9EYnRyQWNjdD4gPERidHJBZ3Q+IDxGaW5JbnN0bklkPiA8Q2xyU3lzTW1iSWQ+IDxDbHJTeXNJZD4gPENkPlVTQUJBPC9DZD4gPC9DbHJTeXNJZD4gPE1tYklkPk1lbWJlciBJZDwvTW1iSWQ+IDwvQ2xyU3lzTW1iSWQ+IDwvRmluSW5zdG5JZD4gPC9EYnRyQWd0PiA8Q2R0VHJmVHhJbmY+IDxQbXRJZD4gPEVuZFRvRW5kSWQ+RW5kLXRvLUVuZC1JZDwvRW5kVG9FbmRJZD4gPC9QbXRJZD4gPEFtdD4gPEluc3RkQW10IENjeT0iVVNEIj5BbW91bnQ8L0luc3RkQW10PiA8L0FtdD4gPENkdHJBZ3Q+IDxGaW5JbnN0bklkPiA8Q2xyU3lzTW1iSWQ+IDxDbHJTeXNJZD4gPENkPlVTQUJBPC9DZD4gPC9DbHJTeXNJZD4gPE1tYklkPk1lbWJlciBJZDwvTW1iSWQ+IDwvQ2xyU3lzTW1iSWQ+IDwvRmluSW5zdG5JZD4gPC9DZHRyQWd0PiA8Q2R0cj4gPE5tPkNyZWRpdG9yIEFjY291bnQgSG9sZGVyIE5hbWU8L05tPiA8L0NkdHI+IDxDZHRyQWNjdD4gPElkPiA8T3Rocj4gPElkPkNyZWRpdG9yIEFjY291bnQgSWQ8L0lkPiA8L090aHI+IDwvSWQ+IDxUcD4gPENkPkNBQ0M8L0NkPiA8L1RwPiA8L0NkdHJBY2N0PiA8L0NkdFRyZlR4SW5mPiA8L1BtdEluZj4gPC9Dc3RtckNkdFRyZkluaXRuPiA8L0RvY3VtZW50Pg");
        paymentObject.setRate(BigDecimal.valueOf(1.78));
        paymentObject.setCreditAmount(BigDecimal.valueOf(1000d));
        paymentObject.setDebitAmount(BigDecimal.valueOf(1000d));
        paymentObject.setDebitCcy("INR");
        paymentObject.setCreditCcy("GBP");
        paymentObject.setWarmUp("N");
        stubEventListener.handleStubEvent(paymentObject);
    }
}
