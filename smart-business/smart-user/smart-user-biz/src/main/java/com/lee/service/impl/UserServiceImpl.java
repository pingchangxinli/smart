package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.api.vo.BusinessUnitVO;
import com.lee.api.vo.SysRoleVO;
import com.lee.api.vo.SysUserVO;
import com.lee.domain.*;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.core.Pagination;
import com.lee.enums.EnabledStatusEnum;
import com.lee.mapper.RoleMapper;
import com.lee.service.SysRoleService;
import com.lee.service.BusinessUnitService;
import com.lee.mapper.UserMapper;
import com.lee.service.SysUserRoleService;
import com.lee.service.UserService;
import com.lee.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lee.li
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private static final String ERROR_ROLE_ARGUMENT_NOT_FOUND = "角色参数不存在";
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private BusinessUnitService businessUnitService;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public SysUserDO findUserByName(String username) {
        SysUserDO user = userMapper.selectOne(new QueryWrapper<SysUserDO>().lambda().eq(SysUserDO::getUsername, username));
        return user;
    }

    @Override
    public LoginUser internalFindUserByUserName(String username) {
        LoginUser loginUser = new LoginUser();
        SysUserDO sysUserDO = this.findUserByName(username);

        if (log.isDebugEnabled()) {
            log.debug("get user from database is : {},userName:{}", sysUserDO, username);
        }
        if (ObjectUtils.isNotEmpty(sysUserDO)) {
            List<SysRoleDO> list = roleMapper.selectRoleListByUserId(sysUserDO.getId());

            BeanUtils.copyProperties(sysUserDO, loginUser);
            List<String> roles = new ArrayList<>();
            list.forEach(sysRole -> {
                roles.add(sysRole.getName());
            });
            loginUser.setRoles(roles);
        } else {
            throw new UsernameNotFoundException("not found username: " + username);
        }
        return loginUser;
    }

    @Override
    public boolean createUser(SysUserDTO userRequest) {
        //新增用户信息
        SysUserDO user = new SysUserDO();
        user.setStatus(EnabledStatusEnum.ENABLED);
        BeanUtils.copyProperties(userRequest, user);
        int count = userMapper.insert(user);
        //增加用户角色
        List<SysUserRole> list = userRequest.getRoles().stream().map(role -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(role.getId());
            sysUserRole.setUserId(user.getId());
            return sysUserRole;
        }).collect(Collectors.toList());

        return sysUserRoleService.saveBatch(list);
    }

    @Override
    public IPage<SysUserVO> pageList(Pagination pagination, SysUserDO sysUserDO) {


        Page<SysUserDO> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());

        QueryWrapper<SysUserDO> queryWrapper = createQueryWhere(sysUserDO);

        IPage<SysUserDO> iPage = userMapper.selectPage(page, queryWrapper);
        //用户权限集合
        List<SysUserDO> list = iPage.getRecords();

        List<SysUserVO> responseList = new ArrayList<>();
        list.forEach(sysUser1 -> {

            SysUserVO sysUserVO = new SysUserVO();
            BeanUtils.copyProperties(sysUser1, sysUserVO);

            //用户角色
            List<SysRoleDTO> roles = sysRoleService.findRoleByUserId(sysUser1.getId());
            List<SysRoleVO> sysRoleVOList = ConvertUtil.convertRoleListDTOToVO(modelMapper, roles);
            sysUserVO.setRoles(sysRoleVOList);

            //用户所在分部详情
            BusinessUnitDTO businessUnit = businessUnitService.findBusinessUnitById(sysUser1.getBusinessUnitId());
            if (log.isDebugEnabled()) {
                log.debug("[UserService] BusinessUnit id:{},BusinessUnit:{}", sysUser1.getBusinessUnitId(),
                        businessUnit);
            }
            if (businessUnit == null || Integer.valueOf(0).equals(sysUser1.getBusinessUnitId())) {
                businessUnit = new BusinessUnitDTO();
                businessUnit.setName("");
            }
            BusinessUnitVO businessUnitVO = modelMapper.map(businessUnit, BusinessUnitVO.class);
            sysUserVO.setBusinessUnit(businessUnitVO);
            responseList.add(sysUserVO);
        });
        IPage<SysUserVO> returnIpage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        returnIpage.setRecords(responseList);
        return returnIpage;

    }

    @Override
    public Integer disabledUserById(Long id) {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setStatus(EnabledStatusEnum.DISABLED);
        sysUserDO.setId(id);
        return userMapper.updateById(sysUserDO);
    }

    @Override
    public List<SysUserDO> findUsers(SysUserDTO sysUserDTO) {
        SysUserDO sysUserDO = modelMapper.map(sysUserDTO, SysUserDO.class);
        QueryWrapper<SysUserDO> queryWrapper = createQueryWhere(sysUserDO);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public int updateUserById(SysUserDTO sysUserRequest) {
        //更新用户信息
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(sysUserRequest, sysUserDO);
        if (log.isDebugEnabled()) {
            log.debug("[UserService updateUserById],sysUser:" + sysUserDO);
        }
        int count = userMapper.updateById(sysUserDO);
        //更新用户角色
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", sysUserRequest.getId());
        sysUserRoleService.remove(queryWrapper);
        List<SysUserRole> list = new ArrayList<>();
        sysUserRequest.getRoles().stream().forEach(role -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUserRequest.getId());
            sysUserRole.setRoleId(role.getId());
            list.add(sysUserRole);
        });
        sysUserRoleService.saveBatch(list);
        return count;
    }

    @Override
    public SysUserDTO findUserById(Long id) {
        SysUserDO sysUserDO = userMapper.selectById(id);
        if (sysUserDO == null) {
            return null;
        }
        return modelMapper.map(sysUserDO, SysUserDTO.class);
    }

    /**
     * 创建查询用户Where条件
     *
     * @param sysUserDO 用户实体类
     * @return 查询条件
     */
    private QueryWrapper<SysUserDO> createQueryWhere(SysUserDO sysUserDO) {
        QueryWrapper<SysUserDO> queryWrapper = new QueryWrapper<>();
        Long id = sysUserDO.getId();
        if (id != null && id > 0) {
            queryWrapper.eq("id", id);
        }
        EnabledStatusEnum status = sysUserDO.getStatus();
        if (status != null) {
            queryWrapper.eq("status", status.getValue());
        }
        String username = sysUserDO.getUsername();
        if (StringUtils.isNotEmpty(username)) {
            queryWrapper.eq("username", username);
        }
        String email = sysUserDO.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            queryWrapper.eq("email", email);
        }
        String phone = sysUserDO.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            queryWrapper.eq("phone", phone);
        }
        return queryWrapper;
    }

}
