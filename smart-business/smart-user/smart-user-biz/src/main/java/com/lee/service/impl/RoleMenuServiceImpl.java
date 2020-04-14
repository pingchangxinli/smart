package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.domain.SysRoleMenu;
import com.lee.mapper.RoleMenuMapper;
import com.lee.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haitao Li
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, SysRoleMenu> implements RoleMenuService {

    @Override
    public Boolean updateBatchByMenuId(Long menuId, List<Long> roleIds) {
        this.remove(new QueryWrapper<SysRoleMenu>().lambda().eq(SysRoleMenu::getMenuId, menuId));
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        roleIds.stream().forEach(roleId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenus.add(sysRoleMenu);
        });
        return this.saveBatch(sysRoleMenus);
    }
}
