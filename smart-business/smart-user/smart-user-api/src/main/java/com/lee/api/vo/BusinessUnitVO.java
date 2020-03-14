package com.lee.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分部
 *
 * @author lee.li
 */
@Data
public class BusinessUnitVO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 分部名称
     */
    private String name;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 是否启用
     */
    @JsonProperty
    private EnabledStatusEnum status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 操作人
     */
    private String operator;
}
