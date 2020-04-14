package com.lee.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.WebApplicationInitializer;

/**
 * 配置服务
 *
 * @author haitao Li
 */
@SpringBootApplication
@EnableConfigServer
public class SmartConfigServerApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SmartConfigServerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartConfigServerApplication.class, args);
    }

}
