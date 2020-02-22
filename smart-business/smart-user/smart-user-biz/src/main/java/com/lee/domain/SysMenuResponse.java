package com.lee.domain;

import lombok.Data;

import java.util.List;

/**
 * 显示用菜单
 *
 * @author lee.li
 */
@Data
public class SysMenuResponse {
    /**
     * 菜单ID
     */
    private Long id;
    /**
     * 菜单父ID
     */
    private Long parentId;
    /**
     * 路径
     */
    private String path;
    /**
     * 图标
     */
    private String icon;
    /**
     * 名称
     */
    private String name;
    /**
     * 子节点
     */
    private List<SysMenuResponse> children;
    private Boolean exact;
    /**
     * 角色集合
     */
    private List<SysRole> roles;
}
