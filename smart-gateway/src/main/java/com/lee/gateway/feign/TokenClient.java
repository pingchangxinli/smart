package com.lee.gateway.feign;

import com.lee.common.core.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @author haitao.li
 */
@FeignClient(value = "smart-auth-server")
public interface TokenClient {
    /**
     * 根据token获取
     * @param token
     * @return
     */
    @GetMapping(value = "/token")
    BaseResponse token(@RequestParam("access_token") String token);

}
