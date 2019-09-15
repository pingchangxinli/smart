package com.lee.feign.server.demo.contorller;

/**
 * @author haitao.li
 */
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang.RandomStringUtils.randomNumeric;

@RestController
public class HelloController {

    @RequestMapping(method = RequestMethod.GET, value = "/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return randomNumeric(2) + name;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String world(@RequestParam String name) {
        return "IVY";
    }
}