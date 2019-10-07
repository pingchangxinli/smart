package com.lee.user.domain;

import lombok.Data;

/**
 * 角色 & URL FILTER 关联关系
 * @author haitao.li
 */
@Data
public class RoleUrlFilterRelation {
    /**
     * 主键
     */
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 链接FIlTER ID
     */
    private Long urlFilterId;
}