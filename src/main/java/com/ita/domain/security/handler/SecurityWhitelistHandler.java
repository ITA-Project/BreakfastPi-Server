package com.ita.domain.security.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component
public class SecurityWhitelistHandler {

  @Value("classpath:security_whitelist.properties")
  private Resource whiteList;

  public String[] getWhiteList() throws IOException {
    Properties props = PropertiesLoaderUtils.loadProperties(whiteList);
    Collection<Object> values = props.values();
    List<String> whiteList = new ArrayList<>();
    values.stream().forEach(value-> {
      if(value.toString().contains(",")) {
        String[] urlCollection = value.toString().split(",");
        for (String url : urlCollection) {
          whiteList.add(url);
        }
      } else {
        whiteList.add(value.toString());
      }
    });
    String[] liString = new String[whiteList.size()];
    return whiteList.toArray(liString);
  }
}
