package com.lee.common.business.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lee.li
 * 请求基础类
 */
@Data
public class BaseDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 分布ID
     */
    private Long businessUitId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
