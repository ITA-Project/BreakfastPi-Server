package com.ita.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "image")
public class ImageConfig {
  private String secretId;
  private String secretKey;
}
