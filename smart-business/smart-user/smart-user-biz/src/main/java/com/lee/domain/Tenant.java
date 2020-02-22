package com.lee.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lee.common.core.BaseObject;
import com.lee.enums.EnabledStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
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
    private EnabledStatus status;
    /**
     * 域名
     */
    private String domain;

}