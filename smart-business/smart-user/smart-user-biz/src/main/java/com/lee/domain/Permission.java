package com.lee.domain;

import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 */
@Data
public class Permission {
    /**
     * 主键.
     */
    private Long id;
    /**
     *名称.
     */
    private String name;
    /**
     *资源类型，[menu|button]
     */
    private String resourceType;
    /**
     *资源路径.
     */
    private String url;
    /**
     *权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;
    /**
     *父编号
     */
    private Long parentId;
    /**
     * 父编号列表
     */
    private String parentIds;
    private Boolean enabled = Boolean.FALSE;
    /**
     *
     */
    private List<SysRoleDO> roles;


}
