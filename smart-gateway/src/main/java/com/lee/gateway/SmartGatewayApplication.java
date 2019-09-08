package com.lee.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 网关启动类,将网关服务注册成为 服务配置客户端
 * @author haitao.li
 */
@SpringBootApplication
@EnableEurekaClient
public class SmartGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartGatewayApplication.class, args);
    }

}
