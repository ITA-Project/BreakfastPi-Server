package com.ita;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Dillon Xie
 * @date 11/13/2020 5:03 PM
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ita.domain.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
