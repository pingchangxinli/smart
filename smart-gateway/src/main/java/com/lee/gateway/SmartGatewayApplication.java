package com.lee.gateway;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 网关启动类,将网关服务注册成为 服务配置客户端
 * @author lee.li
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.lee.gateway.feign"})
public class SmartGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartGatewayApplication.class, args);
    }
}

