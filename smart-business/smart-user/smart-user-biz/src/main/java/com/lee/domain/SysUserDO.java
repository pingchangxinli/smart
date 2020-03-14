package com.lee.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

/**
 * @author lee.li
 * 用户信息类
 */
@Data
@TableName("sys_user")
public class SysUserDO {
    /**
     * 自生成ID
     **/
    protected Long id;
    /**
     * 中文名称
     */
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
    protected Long tenantId;
    /**
     * 客户分部编号
     **/
    protected Long businessUnitId;

    /**
     * 是否可用
     **/
    protected EnabledStatusEnum status;


}
