package com.lee.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.role.domain.SysRole;
import com.lee.role.exception.RoleExistException;
import com.lee.role.mapper.RoleMapper;
import com.lee.role.service.SysRoleService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lee.li
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    private static final String ROLE_EXISTED = "系统中已存在该权限";
    @Resource
    private RoleMapper roleMapper;
    @Override
    public List<SysRole> findRoleByUserId(Long userId) {
        return roleMapper.selectRoleList(userId);
    }

    @Override
    public Integer createRole(SysRole role) throws RoleExistException {
        SysRole tmp = this.finRoleByCode(role.getCode());
        if (ObjectUtils.isNotEmpty(tmp)) {
            throw new RoleExistException(ROLE_EXISTED);
        }
        return roleMapper.insert(role);
    }

    @Override
    public SysRole findRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public IPage<SysRole> findRolePage(String name, Integer currentPage, Integer limit) {
        Page<SysRole> page = new Page<>(currentPage,limit);
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        return roleMapper.selectPage(page,queryWrapper );
    }

    @Override
    public Integer updateRoleById(SysRole role) {
        return roleMapper.updateById(role);
    }

    @Override
    public SysRole finRoleByCode(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code",code);
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysRole> findRoleByIdList(List<Long> list) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id",list);
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysRole> findAllRoles() {
        return roleMapper.selectList(null);
    }


}
