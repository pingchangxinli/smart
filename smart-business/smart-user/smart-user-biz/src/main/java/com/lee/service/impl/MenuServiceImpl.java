package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.core.util.JsonUtil;
import com.lee.enums.EnabledStatusEnum;
import com.lee.util.MenuTreeUtil;
import com.lee.domain.SysMenu;
import com.lee.domain.SysMenuRequest;
import com.lee.domain.SysMenuResponse;
import com.lee.domain.SysRoleMenu;
import com.lee.mapper.MenuMapper;
import com.lee.mapper.RoleMenuMapper;
import com.lee.service.MenuService;
import com.lee.service.RoleMenuService;
import com.lee.domain.SysRole;
import com.lee.domain.SysUserRole;
import com.lee.mapper.SysUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private RoleMenuService roleMenuService;
    /**
     * 默认排序
     */
    private static final Integer DEFAULT_SORT = 1;

    @Override
    public Integer createMenu(SysMenuRequest sysMenuRequest) {
        if (log.isDebugEnabled()) {
            log.debug("[MenuService createMenu] request params: {}", sysMenuRequest);
        }
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuRequest, sysMenu);
        if (log.isDebugEnabled()) {
            log.debug("[MenuService createMenu] request params: {}", sysMenu);
        }
        if (sysMenu.getParentId() == null) {
            sysMenu.setParentId(0L);
        }
        if (sysMenu.getSort() == null) {
            sysMenu.setSort(0);
        }
        sysMenu.setStatus(EnabledStatusEnum.ENABLED);
        int count = menuMapper.insert(sysMenu);
        if (log.isDebugEnabled()) {
            log.debug("[MenuService] create Menu,result id:{}", sysMenu.getId());
        }
        List<Long> roleIds = sysMenuRequest.getRoleIds();
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        roleIds.stream().forEach(id -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(sysMenu.getId());
            sysRoleMenu.setRoleId(id);
            roleMenus.add(sysRoleMenu);
        });
        roleMenuService.saveBatch(roleMenus);
        return count;
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
            sysRoleMenu.setStatus(EnabledStatusEnum.ENABLED);
            sysRoleMenu.setRoleId(roleId);
            roleMenuMapper.insert(sysRoleMenu);
        });
        return roles.size();
    }

    @Override
    public List<SysMenuResponse> findMenusByUserId(Long userId) {
        List<SysMenuResponse> list = new ArrayList<>();
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<SysUserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        List<SysMenu> menus = new ArrayList<>();
        userRoles.forEach(sysUserRole -> {
            List<SysMenu> list1 = this.findMenusByRoleId(sysUserRole.getRoleId());
            menus.addAll(list1);
        });
        menus.stream().forEach(menu -> {
            SysMenuResponse menuResponse = new SysMenuResponse();
            BeanUtils.copyProperties(menu, menuResponse);
            list.add(menuResponse);
        });
        return list;
    }

    @Override
    public List<SysMenuResponse> findMenuTree(SysMenuRequest sysMenuRequest) {
        List<SysMenuResponse> menuResponses = new ArrayList<>();
        List<SysMenu> list = findMenus(sysMenuRequest);
        list.stream().forEach(sysMenu -> {
            SysMenuResponse menuResponse = new SysMenuResponse();
            BeanUtils.copyProperties(sysMenu, menuResponse);
            List<SysRole> sysRoles = roleMenuMapper.selectRolesByMenuId(sysMenu.getId());
            menuResponse.setRoles(sysRoles);
            if (log.isDebugEnabled()) {
                try {
                    log.debug("[MenuService findMenuTree] menu id ：{},sysRoles: {},menuResponse:{}", sysMenu.getId(),
                            JsonUtil.toJson(sysRoles), JsonUtil.toJson(menuResponse));
                } catch (JsonProcessingException e) {
                    log.error("[MenuService findMenuTree] exception:", e);
                }
            }
            menuResponses.add(menuResponse);
        });
        return MenuTreeUtil.buildMenuTree(menuResponses);
    }

    @Override
    public List<SysMenu> findMenuList(SysMenuRequest sysMenuRequest) {
        return findMenus(sysMenuRequest);
    }

    /**
     * 菜单查询
     *
     * @param sysMenuRequest
     * @return
     */
    private List<SysMenu> findMenus(SysMenuRequest sysMenuRequest) {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(sysMenuRequest, menu);

        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();

        String name = menu.getName();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        return menuMapper.selectList(queryWrapper);
    }

    @Override
    public Integer updateMenu(SysMenuRequest sysMenuRequest) {
        int count = 0;
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuRequest, sysMenu);
        Boolean isSucc = roleMenuService.updateBatchByMenuId(sysMenu.getId(), sysMenuRequest.getRoleIds());
        if (isSucc) {
            count = menuMapper.updateById(sysMenu);
        }
        return 0;
    }
}
