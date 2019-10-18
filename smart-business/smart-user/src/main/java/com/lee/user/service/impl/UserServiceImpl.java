package com.lee.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.business.EnabledStatus;
import com.lee.common.bussiness.domain.LoginUser;
import com.lee.role.domain.SysRole;
import com.lee.role.mapper.RoleMapper;
import com.lee.user.domain.SysUser;
import com.lee.user.domain.SysUserRole;
import com.lee.user.mapper.UserMapper;
import com.lee.user.service.SysUserRoleService;
import com.lee.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUser findUserByName(String username) {
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
        return user;
    }

    @Override
    public LoginUser internalFindUserByUserName(String username) {
        LoginUser loginUser = new LoginUser();
        SysUser sysUser = this.findUserByName(username);

        if (log.isDebugEnabled()) {
            log.debug("get user from database is : {},enabled:{}", sysUser, sysUser.getStatus());
        }
        if (ObjectUtils.isNotEmpty(sysUser)) {
            List<SysRole> list = roleMapper.selectRoleList(sysUser.getId());

            BeanUtils.copyProperties(sysUser,loginUser);
            List<String> roles = new ArrayList<>();
            list.forEach(sysRole -> {
                roles.add(sysRole.getCode());
            });
            loginUser.setRoles(roles);
        } else {
            throw new UsernameNotFoundException("not found username: " + username);
        }
        return loginUser;
    }

    @Override
    public boolean createUser(SysUser user){
        user.setStatus(EnabledStatus.ENABLED);
        int count = userMapper.insert(user);
        List<SysUserRole> list =  user.getRoles().stream().map(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(user.getId());
            return sysUserRole;
        }).collect(Collectors.toList());

        return sysUserRoleService.saveBatch(list);
    }

    @Override
    public IPage<SysUser> pageList(Integer currentPage, Integer limit, String userCode, String username) {
        Page<SysUser> page = new Page<>(currentPage,limit);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userCode)) {
            queryWrapper.like("user_code", userCode);
        }
        if (StringUtils.isNotEmpty(username)){
            queryWrapper.like("user_name",username);
        }
        IPage<SysUser> iPage  = userMapper.selectPage(page,queryWrapper);
        List<SysUser> list = iPage.getRecords();
        List<Long> roleIdList = new ArrayList<>();
        list.forEach(sysUser -> {
            List<SysRole> listTmp = roleMapper.selectRoleList(sysUser.getId());
            listTmp.forEach(temp -> {
                roleIdList.add(temp.getId());
            });
            sysUser.setRoles(roleIdList);
        });
        return iPage;

    }

    @Override
    public Integer disabledUserById(Long id) {
        SysUser sysUser = new SysUser();
        sysUser.setStatus(EnabledStatus.DISABLED);
        sysUser.setId(id);
        return userMapper.updateById(sysUser);
    }

}
