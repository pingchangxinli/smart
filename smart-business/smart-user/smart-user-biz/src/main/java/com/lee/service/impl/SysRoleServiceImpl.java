package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.core.Pagination;
import com.lee.domain.SysRoleDO;
import com.lee.domain.SysRoleDTO;
import com.lee.domain.SysRoleDO;
import com.lee.domain.SysRoleDTO;
import com.lee.enums.EnabledStatusEnum;
import com.lee.exception.RoleExistException;
import com.lee.mapper.RoleMapper;
import com.lee.service.SysRoleService;
import com.lee.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haitao Li
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {
    private static final String ROLE_EXISTED = "系统中已存在该权限";
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public List<SysRoleDTO> findRoleByUserId(Long userId) {
        List<SysRoleDO> sysRoleDOList = roleMapper.selectRoleListByUserId(userId);
        return ConvertUtil.convertRoleListDOToDTO(modelMapper, sysRoleDOList);
    }

    @Override
    public Integer createRole(SysRoleDTO role) throws RoleExistException {
        SysRoleDTO tmp = this.finRoleByName(role.getName());
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
    public SysRoleDTO findRoleById(Long id) {
        SysRoleDO sysRoleDO = roleMapper.selectById(id);
        if (sysRoleDO == null) {
            return null;
        }
        return modelMapper.map(sysRoleDO, SysRoleDTO.class);
    }

    @Override
    public IPage<SysRoleDTO> findRolePage(Pagination pagination, SysRoleDTO sysRole) {
        Page<SysRoleDO> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());
        QueryWrapper<SysRoleDO> queryWrapper = new QueryWrapper<>();
        if (sysRole.getName() != null) {
            queryWrapper.like("name", sysRole.getName());
        }
        IPage<SysRoleDO> sysRoleDOIPage = roleMapper.selectPage(page, queryWrapper);
        return ConvertUtil.convertFromDOPage(modelMapper, sysRoleDOIPage);
    }

    @Override
    public Integer updateRoleById(SysRoleDTO role) {
        SysRoleDO sysRoleDO = modelMapper.map(role, SysRoleDO.class);
        return roleMapper.updateById(sysRoleDO);
    }

    @Override
    public SysRoleDTO finRoleByName(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", name);
        SysRoleDO sysRoleDO = roleMapper.selectOne(queryWrapper);
        if (sysRoleDO == null) {
            return null;
        }
        return modelMapper.map(sysRoleDO, SysRoleDTO.class);
    }

    @Override
    public List<SysRoleDTO> findRoleByIdList(List<Long> list) {
        QueryWrapper<SysRoleDO> queryWrapper = new QueryWrapper();
        queryWrapper.in("id", list);
        List<SysRoleDO> sysRoleDOList = roleMapper.selectList(queryWrapper);
        List<SysRoleDTO> sysRoleDTOList = new ArrayList<>();
        sysRoleDOList.forEach(sysRoleDO -> {
            SysRoleDTO sysRoleDTO = modelMapper.map(sysRoleDO, SysRoleDTO.class);
            sysRoleDTOList.add(sysRoleDTO);
        });
        return sysRoleDTOList;
    }

    @Override
    public List<SysRoleDTO> findAllRoles() {
        List<SysRoleDO> sysRoleDOList = roleMapper.selectList(null);
        return ConvertUtil.convertRoleListDOToDTO(modelMapper, sysRoleDOList);
    }

    @Override
    public List<SysRoleDTO> findRolesByStatus(EnabledStatusEnum status) {
        QueryWrapper<SysRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        List<SysRoleDO> sysRoleDOList = roleMapper.selectList(queryWrapper);
        List<SysRoleDTO> sysRoleDTOList = new ArrayList<>();
        sysRoleDOList.forEach(sysRoleDO -> {
            SysRoleDTO sysRoleDTO = modelMapper.map(sysRoleDO, SysRoleDTO.class);
            sysRoleDTOList.add(sysRoleDTO);
        });
        return sysRoleDTOList;
    }


}
