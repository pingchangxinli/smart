package com.lee.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.common.business.EnabledStatus;
import com.lee.menu.domain.SysMenu;
import com.lee.menu.domain.SysRoleMenu;
import com.lee.menu.mapper.MenuMapper;
import com.lee.menu.mapper.RoleMenuMapper;
import com.lee.menu.service.MenuService;
import com.lee.menu.service.RoleMenuService;
import com.lee.user.domain.SysUserRole;
import com.lee.user.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee.li
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private RoleMenuService roleMenuService;
    @Override
    public Integer createMenu(SysMenu sysMenu) {
        sysMenu.setEnabled(EnabledStatus.ENABLED);
        return menuMapper.insert(sysMenu);
    }

    @Override
    public SysMenu findMenuById(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<SysMenu> findMenusByRoleId(Long roleId) {
        return menuMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public int createMenuRoles(Long id, List<Long> roles) {
        roles.forEach(roleId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(id);
            sysRoleMenu.setEnabled(EnabledStatus.ENABLED);
            sysRoleMenu.setRoleId(roleId);
            roleMenuMapper.insert(sysRoleMenu);
        });
        return roles.size();
    }

    @Override
    public List<SysMenu> findMenusByUserId(Long userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<SysUserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        List<SysMenu> menus = new ArrayList<>();
        userRoles.forEach(sysUserRole -> {
            List<SysMenu> list1 = this.findMenusByRoleId(sysUserRole.getRoleId());
            menus.addAll(list1);
        });
        return menus;
    }
}
