package com.lee.feign;

import com.lee.common.core.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lee.li
 */
@FeignClient("SMART-USER")
@RequestMapping("/role")
public interface RoleClient {
    @RequestMapping(value = "/list/ids",method = RequestMethod.GET)
    BaseResponse getRolesByIds(@RequestParam Long[] ids);
}
