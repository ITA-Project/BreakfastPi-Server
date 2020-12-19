package com.ita;

import com.ita.domain.config.MiniProgramerConfig;
import com.ita.domain.config.MqttConfig;
import com.ita.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Dillon Xie
 * @date 11/13/2020 5:03 PM
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.ita.domain.mapper")
@EnableConfigurationProperties({MqttConfig.class, MiniProgramerConfig.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1,1);
    }
}
