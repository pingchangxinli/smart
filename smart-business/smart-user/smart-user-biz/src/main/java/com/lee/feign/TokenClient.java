package com.lee.feign;

import com.lee.common.business.domain.LoginUser;
import com.lee.common.core.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lee.li
 */
@FeignClient("SMART-AUTH")
public interface TokenClient {
    /**
     * 通过token得到用户信息
     *
     * @param accessToken
     * @return
     */
    @GetMapping("/token/user")
    BaseResponse<LoginUser> findUserByAccessToken(@RequestParam("access_token") String accessToken);
}
