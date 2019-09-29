package com.lee.feign;

import com.lee.common.core.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author haitao.li
 */
@FeignClient("SMART-AUTH-SERVER")
public interface TokenClient {
    @GetMapping("/token/user")
    BaseResponse findUserByAccessToken(@RequestParam("access_token") String accessToken);
}
