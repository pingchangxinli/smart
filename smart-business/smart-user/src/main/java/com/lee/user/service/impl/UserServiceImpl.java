package com.lee.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.common.bussiness.domain.LoginUser;
import com.lee.user.domain.SysRole;
import com.lee.user.domain.SysUser;
import com.lee.user.mapper.RoleMapper;
import com.lee.user.mapper.UserMapper;
import com.lee.user.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haitao.li
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Override
    public SysUser findUserByName(String username) {
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername,username));
        return user;
    }

    @Override
    public LoginUser internalFindUserByUserName(String username) {
        LoginUser loginUser = new LoginUser();
        SysUser sysUser = this.findUserByName(username);

        if(logger.isDebugEnabled()) {
            logger.debug("get user from database is : {},enabled:{}",sysUser,sysUser.getEnabled());
        }
        if (ObjectUtils.isNotEmpty(sysUser)) {
            List<SysRole> list = roleMapper.selectRoleList(sysUser.getId());

            loginUser.setUsername(sysUser.getUsername());
            loginUser.setPassword(sysUser.getPassword());
            int value = sysUser.getEnabled().getValue();
            if(value == 0) {
                loginUser.setEnabled(true);
            } else {
                loginUser.setEnabled(false);
            }
            List<String> roles = new ArrayList<>();
            list.forEach(sysRole -> {
                roles.add(sysRole.getCode());
            });
            loginUser.setRoles(roles);
        } else {
            throw new UsernameNotFoundException("not found username: "+username);
        }
        return loginUser;
    }
}
