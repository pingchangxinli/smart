package com.lee.demo.api;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author haitao.li
 */
@RestController
public class DemoApi {
    @Resource
    private EurekaClient client;
    @GetMapping("/client")
    public void serviceUrl() {
        System.out.println(client);
    }
}
