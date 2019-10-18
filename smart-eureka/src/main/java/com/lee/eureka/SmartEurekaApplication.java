package com.lee.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author lee.li
 */
@SpringBootApplication
@EnableEurekaServer
public class SmartEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartEurekaApplication.class, args);
    }
}
