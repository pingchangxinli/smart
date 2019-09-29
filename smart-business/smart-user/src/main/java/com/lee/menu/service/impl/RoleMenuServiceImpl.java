package com.lee.menu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.menu.domain.SysRoleMenu;
import com.lee.menu.mapper.SysRoleMenuMapper;
import com.lee.menu.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @author haitao.li
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements RoleMenuService {
}
