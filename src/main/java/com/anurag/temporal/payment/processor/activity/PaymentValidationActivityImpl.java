package com.anurag.temporal.payment.processor.activity;

import com.anurag.temporal.payment.processor.constant.ActivityStageEnum;
import com.anurag.temporal.payment.processor.model.PaymentObject;
import com.anurag.temporal.payment.processor.model.PaymentValidationActivityObject;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.SAXBuilder;
import org.springframework.web.client.RestTemplate;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class PaymentValidationActivityImpl implements PaymentValidationActivity{

    public static final String respone = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.03\">\n" +
            "    <CstmrCdtTrfRpt>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>Message-Id</MsgId>\n" +
            "            <CreDtTm>2024-05-10T16:10:02.017+00:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmDt>YYYY-MM-DD</SttlmDt>\n" +
            "            </SttlmInf>\n" +
            "            <InitgPty>\n" +
            "                <Id>\n" +
            "                    <OrgId>\n" +
            "                        <Othr>\n" +
            "                            <Id>Client-Id</Id>\n" +
            "                        </Othr>\n" +
            "                    </OrgId>\n" +
            "                </Id>\n" +
            "            </InitgPty>\n" +
            "        </GrpHdr>\n" +
            "        <Rpt>\n" +
            "            <OrgnlMsgId>Message-Id</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>Batch-Id</OrgnlMsgNmId>\n" +
            "            <RptId>Report-Id</RptId>\n" +
            "            <CreDtTm>2024-05-10T16:10:02.017+00:00</CreDtTm>\n" +
            "            <AcctRpt>\n" +
            "                <Acct>\n" +
            "                    <Id>\n" +
            "                        <Othr>\n" +
            "                            <Id>Debtor Account Id</Id>\n" +
            "                        </Othr>\n" +
            "                    </Id>\n" +
            "                </Acct>\n" +
            "                <TtlNtriesCnt>1</TtlNtriesCnt>\n" +
            "                <ntry>\n" +
            "                    <NtryDtTm>2024-05-10T16:10:02.017+00:00</NtryDtTm>\n" +
            "                    <Amt>\n" +
            "                        <InstdAmt Ccy=\"USD\">Amount</InstdAmt>\n" +
            "                    </Amt>\n" +
            "                    <CdtDbtInd>DBIT</CdtDbtInd>\n" +
            "                    <Sts>\n" +
            "                        <Prtry>Success/Failure Status</Prtry> \n" +
            "                    </Sts>\n" +
            "                    <Rsn>\n" +
            "                        <RsnCd>\n" +
            "                            <Prtry>Reason Code</Prtry> \n" +
            "                        </RsnCd>\n" +
            "                    </Rsn>\n" +
            "                    <AddtlNtryInf>\n" +
            "                        <OrgnlEndToEndId>End-to-End-Id</OrgnlEndToEndId>\n" +
            "                    </AddtlNtryInf>\n" +
            "                </ntry>\n" +
            "            </AcctRpt>\n" +
            "        </Rpt>\n" +
            "    </CstmrCdtTrfRpt>\n" +
            "</Document>";


    @Override
    public PaymentObject validate(PaymentObject paymentObject) throws IOException, JDOMException {
        log.info("Received message {} in {}",paymentObject.toString(),this.getClass());
        RestTemplate restTemplate = new RestTemplate();
        String valid = restTemplate.getForObject(String.format("http://localhost:9999/dupcheck?messageId=%s",paymentObject.getId()),String.class);
        List<String> list = new ArrayList<>();
        if(Boolean.parseBoolean(valid)){
            var message = new String(Base64.getDecoder().decode(paymentObject.getMessage()))  ;
            if(!message.contains("<") && !valideXml(message)){
                list.add(String.format("Improper message []",message));
            }
        }else {
            list.add(String.format("%s is duplicate",paymentObject.getId()));
        }

          paymentObject.getActivityObjectMap().put(ActivityStageEnum.VALIDATION.name(),
                 PaymentValidationActivityObject.builder().validated(list.size()>0)
                         .validationFailureList(list).build());
        paymentObject.setResponseString(Base64.getEncoder().encodeToString(respone.getBytes(StandardCharsets.UTF_8)));
          return paymentObject;
    }

    public boolean valideXml(String xml) throws JDOMException, IOException
    {
        SAXBuilder builder = new SAXBuilder();
        try{
            Document doc = builder.build(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JDOMParseException ex) {
            return false;
        }
        return true;
    }
}
