package com.lee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 *
 * @author lee.li
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class SmartWorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartWorkApplication.class, args);
    }
}
