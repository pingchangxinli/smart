package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.core.Pagination;
import com.lee.domain.SysRoleDO;
import com.lee.domain.SysRoleDTO;
import com.lee.domain.SysRoleVO;
import com.lee.enums.EnabledStatusEnum;
import com.lee.exception.RoleExistException;
import com.lee.mapper.RoleMapper;
import com.lee.service.SysRoleService;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lee.li
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    private static final String ROLE_EXISTED = "系统中已存在该权限";
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public List<SysRoleDO> findRoleByUserId(Long userId) {
        return roleMapper.selectRoleList(userId);
    }

    @Override
    public Integer createRole(SysRoleDTO role) throws RoleExistException {
        SysRoleDO tmp = this.finRoleByName(role.getName());
        if (ObjectUtils.isNotEmpty(tmp)) {
            throw new RoleExistException(ROLE_EXISTED);
        }
        SysRoleDO sysRoleDO = modelMapper.map(role, SysRoleDO.class);
        sysRoleDO.setCreateTime(LocalDateTime.now());
        sysRoleDO.setUpdateTime(LocalDateTime.now());
        sysRoleDO.setStatus(EnabledStatusEnum.ENABLED);
        return roleMapper.insert(sysRoleDO);
    }

    @Override
    public SysRoleDO findRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public IPage<SysRoleDO> findRolePage(Pagination pagination, SysRoleDTO sysRole) {
        Page<SysRoleDO> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());
        QueryWrapper<SysRoleDO> queryWrapper = new QueryWrapper<>();
        if (sysRole.getName() != null) {
            queryWrapper.like("name", sysRole.getName());
        }
        return roleMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Integer updateRoleById(SysRoleDO role) {
        return roleMapper.updateById(role);
    }

    @Override
    public SysRoleDO finRoleByName(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", name);
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysRoleDO> findRoleByIdList(List<Long> list) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id", list);
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysRoleDO> findAllRoles() {
        return roleMapper.selectList(null);
    }


}
