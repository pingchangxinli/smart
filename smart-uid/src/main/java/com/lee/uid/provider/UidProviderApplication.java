package com.lee.uid.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author haitao.li
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value = {"com.lee.uid.provider","com.baidu.fsg.uid"})
public class UidProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(UidProviderApplication.class, args);
    }

}
