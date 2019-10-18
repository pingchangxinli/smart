package com.lee.gateway.feign;

import com.lee.common.core.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 获取租户信息
 *
 * @author lee.li
 */
@FeignClient("SMART-USER")
@RequestMapping("/tenant")
public interface TenantClient {
    /**
     * 通过域名查询租户
     * @param domain 域名
     * @return
     */
    @GetMapping("/domain/id")
    BaseResponse findTenantByDomain(@RequestParam("domain") String domain);
}
