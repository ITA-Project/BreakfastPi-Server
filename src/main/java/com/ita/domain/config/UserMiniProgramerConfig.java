package com.ita.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "userminiprogramer")
public class UserMiniProgramerConfig {
  private String appId;
  private String appSecret;
}
