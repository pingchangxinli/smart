package com.lee.feign.client.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * @author haitao.li
 */
@FeignClient("feignServer")
public interface HelloClient {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String users(@RequestParam("name") String name);

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    String users1(@PathVariable("name") String name);
}
