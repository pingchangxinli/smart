package com.lee.tenant.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.common.business.enums.EnabledStatusEnum;
import com.lee.common.core.BaseObject;
import lombok.Data;

/**
 * 成本中心
 * @author lee.li
 */
@Data
@TableName("sys_cost_center")
public class CostCenter extends BaseObject {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 成本中心名称
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
    private EnabledStatusEnum status;
}
