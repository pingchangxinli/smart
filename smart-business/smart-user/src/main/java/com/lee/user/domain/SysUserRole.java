package com.lee.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户 角色 对应表
 * @author lee.li
 */
@Data
@TableName(value="sys_user_role")
public class SysUserRole {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 角色ID
     */
    @TableField(value= "role_id")
    private Long roleId;
    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    private Long tenantId;
}
