package com.lee.auth.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lee.li
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.lee.auth.server.feign"})
@MapperScan(basePackages = "com.lee.auth.server.mapper")
public class SmartAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAuthServerApplication.class, args);
    }

}
