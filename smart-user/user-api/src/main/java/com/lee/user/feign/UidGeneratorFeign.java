package com.lee.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author haitao.li
 */
@FeignClient("smart-uid-server")
public interface UidGeneratorFeign {
    /**
     * 通过UID服务,获取UID
     * @return UID
     */
    @GetMapping("/uid")
    Long getUid();
}
