package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.constant.ActivityEventEnum;
import com.anurag.temporal.payment.processor.constant.ActivityStageEnum;
import com.anurag.temporal.payment.processor.event.PaymentEvent;
import com.anurag.temporal.payment.processor.event.listener.CustomActivityEventListener;
import com.anurag.temporal.payment.processor.event.mongo.MongoEvent;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.PaymentValidationActivityObject;
import com.newrelic.api.agent.Trace;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.SAXBuilder;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class PaymentValidationActivityImpl implements PaymentValidationActivity{

    private final CustomActivityEventListener customActivityEventListener;

    public static final String respone = """
            <?xml version="1.0" encoding="UTF-8" ?>
            <Document xmlns="urn:iso:std:iso:20022:tech:xsd:pain.002.001.03">
                <CstmrCdtTrfRpt>
                    <GrpHdr>
                        <MsgId>Message-Id</MsgId>
                        <CreDtTm>2024-05-10T16:10:02.017+00:00</CreDtTm>
                        <NbOfTxs>1</NbOfTxs>
                        <SttlmInf>
                            <SttlmDt>YYYY-MM-DD</SttlmDt>
                        </SttlmInf>
                        <InitgPty>
                            <Id>
                                <OrgId>
                                    <Othr>
                                        <Id>Client-Id</Id>
                                    </Othr>
                                </OrgId>
                            </Id>
                        </InitgPty>
                    </GrpHdr>
                    <Rpt>
                        <OrgnlMsgId>Message-Id</OrgnlMsgId>
                        <OrgnlMsgNmId>Batch-Id</OrgnlMsgNmId>
                        <RptId>Report-Id</RptId>
                        <CreDtTm>2024-05-10T16:10:02.017+00:00</CreDtTm>
                        <AcctRpt>
                            <Acct>
                                <Id>
                                    <Othr>
                                        <Id>Debtor Account Id</Id>
                                    </Othr>
                                </Id>
                            </Acct>
                            <TtlNtriesCnt>1</TtlNtriesCnt>
                            <ntry>
                                <NtryDtTm>2024-05-10T16:10:02.017+00:00</NtryDtTm>
                                <Amt>
                                    <InstdAmt Ccy="USD">Amount</InstdAmt>
                                </Amt>
                                <CdtDbtInd>DBIT</CdtDbtInd>
                                <Sts>
                                    <Prtry>Success/Failure Status</Prtry>\s
                                </Sts>
                                <Rsn>
                                    <RsnCd>
                                        <Prtry>Reason Code</Prtry>\s
                                    </RsnCd>
                                </Rsn>
                                <AddtlNtryInf>
                                    <OrgnlEndToEndId>End-to-End-Id</OrgnlEndToEndId>
                                </AddtlNtryInf>
                            </ntry>
                        </AcctRpt>
                    </Rpt>
                </CstmrCdtTrfRpt>
            </Document>""";

    public PaymentValidationActivityImpl(CustomActivityEventListener customActivityEventListener) {
        this.customActivityEventListener = customActivityEventListener;
    }

    @Trace(metricName = "SanctionRequest",nameTransaction = true,dispatcher = true)
    @Override
    public PaymentObject validate(PaymentObject paymentObject) throws IOException, JDOMException {
        log.info("Received message {} in {}",paymentObject.toString(),this.getClass());
        RestTemplate restTemplate = new RestTemplate();
        String valid = restTemplate.getForObject(String.format("http://localhost:9999/dupcheck?messageId=%s",paymentObject.getId()),String.class);
        List<String> list = new ArrayList<>();
        customActivityEventListener.handleActivityEvent(PaymentEvent
                .builder()
                .activityEventEnum(ActivityEventEnum.MONGO_EVENT)
                .mongoEvent(MongoEvent.builder().workflowId("Payment_"+paymentObject.getId())
                        .message("from payment validation activity for workflow id Payment_"+paymentObject.getId()).build()
                        )
                .build());
        if(Boolean.parseBoolean(valid)){
            var message = new String(Base64.getDecoder().decode(paymentObject.getMessage()))  ;
            if(!message.contains("<") && !valideXml(message)){
                list.add(String.format("Improper message %s",message));
            }
        }else {
            list.add(String.format("%s is duplicate",paymentObject.getId()));
        }

          paymentObject.getActivityObjectMap().put(ActivityStageEnum.VALIDATION.name(),
                 PaymentValidationActivityObject.builder().validated(!list.isEmpty())
                         .validationFailureList(list).build());
        paymentObject.setResponseString(Base64.getEncoder().encodeToString(respone.getBytes(StandardCharsets.UTF_8)));
          return paymentObject;
    }

    public boolean valideXml(String xml) throws JDOMException, IOException
    {
        SAXBuilder builder = new SAXBuilder();
        try{
            builder.build(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JDOMParseException ex) {
            return false;
        }
        return true;
    }
}
