package com.lee.user.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lee.EnabledStatus;
import lombok.Data;

import java.util.List;

/**
 * @author haitao.li
 * 用户信息类
 */
@Data
public class SysUser {
    /**
     * 自生成ID
     **/
    private Long id;
    /**
     * 用户自定义Code
     */
    @JsonProperty("user_code")
    private String userCode;
    /**
     * 用户名
     **/
    private String username;
    /**
     * 密码
     **/
    private String password;
    /**
     * 邮箱
     **/
    private String email;
    /**
     * 手机号
     **/
    private String phone;
    /**
     * 租户ID
     **/
    @JsonProperty("tenant_id")
    private Integer tenantId;
    /**
     * 客户门店编号
     **/
    @JsonProperty("shop_id")
    private Integer shopId;

    /**
     * 是否可用
     **/
    @JsonValue
    private EnabledStatus enabled;
    /**
     * 用户角色集合
     */
    @TableField(exist = false)
    private List<SysRole> roles;

}
