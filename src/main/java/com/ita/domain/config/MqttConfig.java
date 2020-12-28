package com.ita.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig {
    private String broker;
    private String liveTime;
    private String topic;
    private String connStr;
    private String clientId;
}
