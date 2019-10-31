package com.lee.tenant.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.common.business.enums.EnabledStatusEnum;
import com.lee.common.core.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 租户
 * @author lee.li
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("sys_tenant")
public class Tenant extends BaseObject {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 公司名称
     */
    @JsonProperty("company_name")
    private String companyName;
    /**
     * 状态
     */
    private EnabledStatusEnum status;
    /**
     * 域名
     */
    private String domain;

}
