package com.lee.menu.service;

import com.lee.menu.domain.SysMenu;
import com.lee.menu.domain.SysMenuRequest;
import com.lee.menu.domain.SysMenuResponse;

import java.util.List;

/**
 * @author lee.li
 */
public interface MenuService {
    /**
     * 新增菜单
     *
     * @param sysMenuRequest 菜单对象
     * @return 新增条数
     */
    Integer createMenu(SysMenuRequest sysMenuRequest);

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
     * 查询用户下的可看的所有菜单
     *
     * @param userId 用户ID
     * @return
     */
    List<SysMenuResponse> findMenusByUserId(Long userId);

    /**
     * 树状展示菜单
     *
     * @param sysMenuRequest
     * @return
     */
    List<SysMenuResponse> findMenuTree(SysMenuRequest sysMenuRequest);

    /**
     * 查询菜单
     *
     * @param menu
     * @return
     */
    List<SysMenu> findMenuList(SysMenuRequest menu);

    /**
     * 为菜单创建角色属性
     *
     * @param id
     * @param roles
     * @return
     */
    int createMenuRoles(Long id, List<Long> roles);

    /**
     * 更新菜单信息 及其 菜单角色
     *
     * @param sysMenuRequest
     * @return
     */
    Integer updateMenu(SysMenuRequest sysMenuRequest);


}
