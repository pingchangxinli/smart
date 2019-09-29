package com.lee.role.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.lee.common.business.EnabledStatus;
import com.lee.common.core.BaseObject;
import com.lee.user.domain.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author haitao.li
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "sys_role")
public class SysRole extends BaseObject {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的:
     */
    private String code;
    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private EnabledStatus enabled;
    /**
     * 权限 和 角色 是多对多关心
     */
    @TableField(exist = false)
    private List<Permission> permissions;

    @TableField("tenant_id")
    private Long tenantId;
}
