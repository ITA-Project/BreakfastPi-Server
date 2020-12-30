package com.ita.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "riderminiprogramer")
public class RiderMiniProgramerConfig {
  private String appId;
  private String appSecret;
}
