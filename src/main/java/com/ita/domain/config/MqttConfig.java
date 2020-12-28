package com.ita.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig {
    private String broker;
    private String liveTime;
    private String topic;
    private String connStr;
    private String clientId;
}
