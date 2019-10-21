package com.lee.gateway.feign;

import com.lee.common.core.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @author lee.li
 */
@FeignClient(value = "SMART-AUTH-SERVER")
public interface TokenClient {
    /**
     * 根据token获取
     * @param token
     * @return
     */
    @GetMapping(value = "/token")
    BaseResponse token(@RequestHeader("Authorization") String authorization,
                       @RequestParam("access_token") String token);

}
