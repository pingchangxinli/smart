package com.lee.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.enums.EnabledStatusEnum;
import com.lee.common.core.BaseObject;
import lombok.Data;

/**
 * 分部
 *
 * @author lee.li
 */
@Data
@TableName("sys_business_unit")
public class BusinessUnit extends BaseObject {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 分部名称
     */
    private String name;
    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    private Long tenantId;
    /**
     * 是否启用
     */
    @JsonProperty
    private EnabledStatusEnum status;
}
