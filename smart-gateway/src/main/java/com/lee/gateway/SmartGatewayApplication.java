package com.lee.gateway;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;


/**
 * 网关启动类,将网关服务注册成为 服务配置客户端
 *
 * @author lee.li
 */
@SpringBootApplication
public class SmartGatewayApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SmartGatewayApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartGatewayApplication.class, args);
    }
}

