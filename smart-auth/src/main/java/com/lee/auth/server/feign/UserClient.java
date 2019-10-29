package com.lee.auth.server.feign;

import com.lee.common.bussiness.domain.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 请求用户信息,该请求会将原始请求头信息传入user服务,请求头传入逻辑为: SecuringRequestInterceptor
 * @author lee.li
 */
@FeignClient("SMART-USER")
@RequestMapping("/user")
public interface UserClient {
    /**
     * 用户名得到用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping(value = "/internal/{username}")
    LoginUser findUserByUserName(@PathVariable("username") String username);

}
