package com.lee.feign.client.demo.controller;

import com.lee.feign.client.demo.feign.HelloClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @author haitao.li
 */
@RestController
public class HelloContorller {
    private static final Logger logger = LoggerFactory.getLogger(HelloContorller.class);
    @Autowired
    private HelloClient helloClient;
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        logger.info("feign client come in hello method start");
        String a = helloClient.users("1111");
        logger.info("feign client come in hello method end, result: {}",a);
        return a;
    }
    @RequestMapping(value = "/hello/{name}",method = RequestMethod.GET)
    public String hello1(@PathVariable("name") String name) {
        logger.info("feign client come in hello method start");
        String a = helloClient.users1(name);
        logger.info("feign client come in hello method end, result: {}",a);
        return a;
    }
}
