package com.lee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.domain.SysRoleMenu;

import java.util.List;

/**
 * @author haitao Li
 */
public interface RoleMenuService extends IService<SysRoleMenu> {
    /**
     * 依据 菜单ID 批量更新角色
     *
     * @param menuId  菜单ID
     * @param roleIds 角色列表
     * @return
     */
    Boolean updateBatchByMenuId(Long menuId, List<Long> roleIds);
}
