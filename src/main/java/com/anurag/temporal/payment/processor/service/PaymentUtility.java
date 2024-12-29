package com.anurag.temporal.payment.processor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PaymentUtility {
    public static String xmlToJson(String pain001v9) throws IOException {
        var xml = new String(Base64.getDecoder().decode(pain001v9.getBytes(StandardCharsets.UTF_8)));
        XmlMapper xmlMapper = new XmlMapper();
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(xmlMapper.readTree(xml));
    }
}
