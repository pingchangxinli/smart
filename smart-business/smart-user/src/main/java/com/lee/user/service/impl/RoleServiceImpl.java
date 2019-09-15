package com.lee.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.user.domain.SysRole;
import com.lee.user.mapper.RoleMapper;
import com.lee.user.service.RoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haitao.li
 */
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Override
    public List<SysRole> findRoleByUserId(Long userId) {
        return roleMapper.selectRoleList(userId);
    }
}
