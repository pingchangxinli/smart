package com.lee.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author haitao.li
 */
@Data
@AllArgsConstructor
public class Role {
    /**
     * 编号
     */

    private Long id;
    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的:
     */
    private String role;
    /**
     * 角色描述,UI界面显示使用
     */
    private String description;
    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean enabled = Boolean.FALSE;
    /**
     * 权限 和 角色 是多对多关心
     */
    private List<Permission> permissions;
}
