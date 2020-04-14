package com.lee.common.business.domain;

import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author haitao Li
 * 请求基础类
 */
@Data
public class BaseDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 分部ID
     */
    private Long businessUnitId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 生效或者失效
     */
    private EnabledStatusEnum status;
}
