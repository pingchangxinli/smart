package com.lee.api.feign;

import com.lee.api.vo.BusinessUnitVO;
import com.lee.api.vo.SysUserVO;
import com.lee.common.core.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户请求
 *
 * @author haitao Li
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
    BaseResponse<SysUserVO> getCurrentUser(@RequestHeader("authorization") String authorization);

    /**
     * 获取分部列表
     *
     * @param authorization 头部授权信息
     * @param needAll       根据token 无法返回分部，true则返回所有的分部
     * @return
     */
    @GetMapping("businessUnit/listByAuth")
    BaseResponse<List<BusinessUnitVO>> getBusinessUnits(@RequestHeader("authorization") String authorization,
                                                        @RequestParam("needAll") Boolean needAll);

    @GetMapping("businessUnit/{id}")
    BaseResponse<BusinessUnitVO> getBusinessUnitById(@RequestHeader("authorization") String authorization,
                                                     @PathVariable("id") Long id);
}
