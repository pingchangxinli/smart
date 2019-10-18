package com.lee.menu.service;

import com.lee.menu.domain.SysMenu;

import java.util.List;

/**
 * @author lee.li
 */
public interface MenuService {
    /**
     * 新增菜单
     * @param sysMenu 菜单对象
     * @return 新增条数
     */
    Integer createMenu(SysMenu sysMenu) ;

    /**
     * 通过menuid查询menu
     * @param id ID标识
     * @return menu对象
     */
    SysMenu findMenuById(Long id);

    /**
     * 查询出角色下的所有菜单
     * @param roleId
     * @return
     */
    List<SysMenu> findMenusByRoleId(Long roleId);

    /**
     * 为菜单创建角色属性
     * @param id
     * @param roles
     * @return
     */
    int createMenuRoles(Long id, List<Long> roles);

    /**
     * 查询用户下的可看的所有菜单
     * @param userId 用户ID
     * @return
     */
    List<SysMenu> findMenusByUserId(Long userId);
}
