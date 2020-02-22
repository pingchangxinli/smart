package com.lee.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.WebApplicationInitializer;

/**
 * @author lee.li
 */
@SpringBootApplication
@EnableEurekaServer
public class SmartEurekaApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SmartEurekaApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartEurekaApplication.class, args);
    }
}
