package com.ita.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "miniprogramer")
public class MiniProgramerConfig {
  private String appId;
  private String appSecret;
}
