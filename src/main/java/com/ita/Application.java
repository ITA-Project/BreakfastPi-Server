package com.ita;

import com.ita.domain.config.MqttConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Dillon Xie
 * @date 11/13/2020 5:03 PM
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ita.domain.mapper")
@EnableConfigurationProperties(MqttConfig.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
