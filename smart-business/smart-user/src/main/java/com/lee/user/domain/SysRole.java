package com.lee.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.management.relation.RoleStatus;
import java.util.List;

/**
 * @author haitao.li
 */
@Data
@AllArgsConstructor
@TableName(value = "sys_role")
public class SysRole {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.INPUT)
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
     * 角色描述,UI界面显示使用
     */
    private String desc;
    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private RoleStatus enabled;
    /**
     * 权限 和 角色 是多对多关心
     */
    @TableField(exist = false)
    private List<Permission> permissions;
}
