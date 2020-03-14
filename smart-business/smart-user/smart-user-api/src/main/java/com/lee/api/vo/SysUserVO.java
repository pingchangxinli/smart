package com.lee.api.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 * 用户信息类
 */
@Data
public class SysUserVO {
    /**
     * 自生成ID
     **/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 中文名称
     */
    private String chineseName;
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
    private Long tenantId;

    /**
     * 是否可用
     **/
    private EnabledStatusEnum status;
    /**
     * 分部明细
     */
    private BusinessUnitVO businessUnit;
    /**
     * 用户所拥有角色
     */
    private List<SysRoleVO> roles;
}
