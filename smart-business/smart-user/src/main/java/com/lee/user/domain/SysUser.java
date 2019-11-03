package com.lee.user.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.common.business.EnabledStatus;
import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 * 用户信息类
 */
@Data
@TableName("sys_user")
public class SysUser {
    /**
     * 自生成ID
     **/
    protected Long id;
    /**
     * 中文名称
     */
    @JsonProperty("chinese_name")
    @TableField("chinese_name")
    protected String chineseName;
    /**
     * 用户名
     **/
    protected String username;
    /**
     * 密码
     **/
    protected String password;
    /**
     * 邮箱
     **/
    protected String email;
    /**
     * 手机号
     **/
    protected String phone;
    /**
     * 租户ID
     **/
    @JsonProperty("tenant_id")
    @TableField("tenant_id")
    protected Long tenantId;
    /**
     * 客户成本中心编号
     **/
    @JsonProperty("cost_center_id")
    @TableField("cost_center_id")
    protected Long costCenterId;

    /**
     * 是否可用
     **/
    protected EnabledStatus status;


}
