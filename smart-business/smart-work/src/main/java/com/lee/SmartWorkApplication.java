package com.lee;

import com.lee.common.security.annotation.EnableSmartResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.WebApplicationInitializer;

/**
 * 启动类
 *
 * @author lee.li
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableSmartResourceServer
public class SmartWorkApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SmartWorkApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartWorkApplication.class, args);
    }
}
