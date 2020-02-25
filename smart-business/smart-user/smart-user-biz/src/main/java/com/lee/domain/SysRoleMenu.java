package com.lee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

/**
 * @author lee.li
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu {
    @TableId(type= IdType.AUTO)
    private Long id;
    @TableField("role_id")
    private Long roleId;
    @TableField("menu_id")
    private Long menuId;
    @TableField("tenant_id")
    private Long tenantId;
    private EnabledStatusEnum status;
}
