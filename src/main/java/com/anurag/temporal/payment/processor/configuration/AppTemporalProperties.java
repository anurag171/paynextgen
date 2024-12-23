package com.anurag.temporal.payment.processor.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.temporal.setting")
@Getter
@Setter
@ToString
public class AppTemporalProperties {
    private String host;
    private String port;
    private String queue;
    private String healthCheck;
    private String remoteStub;
    private String appCreator;
    private String namespace;
}
