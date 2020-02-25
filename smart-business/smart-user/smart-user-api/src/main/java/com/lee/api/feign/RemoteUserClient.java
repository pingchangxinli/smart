package com.lee.api.feign;

import com.lee.api.entity.BusinessUnit;
import com.lee.api.entity.SysUser;
import com.lee.common.core.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户请求
 *
 * @author lee.li
 */
@FeignClient("SMART-USER")
public interface RemoteUserClient {
    /**
     * 根据用户ID得到用户信息
     *
     * @param authorization 头部授权信息
     * @return
     */
    @GetMapping("/user/currentUser")
    BaseResponse<SysUser> getCurrentUser(@RequestHeader("authorization") String authorization);

    /**
     * 获取分部列表
     *
     * @param authorization 头部授权信息
     * @param tenantId
     * @return
     */
    @GetMapping("businessUnit/list")
    BaseResponse<List<BusinessUnit>> getBusinessUnits(@RequestHeader("authorization") String authorization,
                                                      @RequestParam("tenantId") Long tenantId);
}
