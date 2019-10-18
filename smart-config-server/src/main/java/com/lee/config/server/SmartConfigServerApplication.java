package com.lee.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置服务
 * @author lee.li
 */
@SpringBootApplication
@EnableConfigServer
public class SmartConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartConfigServerApplication.class, args);
    }

}
