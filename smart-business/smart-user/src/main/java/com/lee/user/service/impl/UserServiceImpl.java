package com.lee.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.business.EnabledStatus;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.core.Pagination;
import com.lee.common.core.util.JsonUtil;
import com.lee.role.domain.SysRole;
import com.lee.role.mapper.RoleMapper;
import com.lee.role.service.SysRoleService;
import com.lee.tenant.domain.CostCenter;
import com.lee.tenant.service.CostCenterService;
import com.lee.user.domain.SysUser;
import com.lee.user.domain.SysUserRequest;
import com.lee.user.domain.SysUserResponse;
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
    private SysRoleService sysRoleService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private CostCenterService costCenterService;

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

            BeanUtils.copyProperties(sysUser, loginUser);
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
    public boolean createUser(SysUserRequest userRequest) {
        //新增用户信息
        SysUser user = new SysUser();
        user.setStatus(EnabledStatus.ENABLED);
        BeanUtils.copyProperties(userRequest, user);
        int count = userMapper.insert(user);
        //增加用户角色
        List<SysUserRole> list = userRequest.getRoles().stream().map(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userRequest.getId());
            return sysUserRole;
        }).collect(Collectors.toList());

        return sysUserRoleService.saveBatch(list);
    }

    @Override
    public IPage<SysUserResponse> pageList(Pagination pagination, SysUser sysUser) {


        Page<SysUser> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());

        QueryWrapper<SysUser> queryWrapper = createQueryWhere(sysUser);

        IPage<SysUser> iPage = userMapper.selectPage(page, queryWrapper);
        //用户权限集合
        List<SysUser> list = iPage.getRecords();

        List<SysUserResponse> responseList = new ArrayList<>();
        list.stream().forEach(sysUser1 -> {
            //用户角色
            List<SysRole> roles = sysRoleService.findRoleByUserId(sysUser1.getId());
            SysUserResponse sysUserResponse = new SysUserResponse();
            BeanUtils.copyProperties(sysUser1, sysUserResponse);
            sysUserResponse.setRoles(roles);
            //用户所在成本中心详情
            CostCenter costCenter = costCenterService.findCostCenterById(sysUser1.getCostCenterId());
            if (log.isDebugEnabled()) {
                log.debug("[UserService] costCenter id:{},costCenter:{}", sysUser1.getCostCenterId(),
                        costCenter);
            }
            sysUserResponse.setCostCenter(costCenter);
            responseList.add(sysUserResponse);
        });
        IPage<SysUserResponse> returnIpage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        returnIpage.setRecords(responseList);
        return returnIpage;

    }

    @Override
    public Integer disabledUserById(Long id) {
        SysUser sysUser = new SysUser();
        sysUser.setStatus(EnabledStatus.DISABLED);
        sysUser.setId(id);
        return userMapper.updateById(sysUser);
    }

    @Override
    public List<SysUser> findUsers(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = createQueryWhere(sysUser);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public int updateUserById(SysUserRequest sysUserRequest) {
        //更新用户信息
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRequest, sysUser);
        int count = userMapper.updateById(sysUser);
        //更新用户角色
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", sysUserRequest.getId());
        sysUserRoleService.remove(queryWrapper);
        sysUserRequest.getRoles().stream().forEach(role -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUserRequest.getId());
        });

        return 0;
    }

    /**
     * 创建查询用户Where条件
     *
     * @param sysUser 用户实体类
     * @return 查询条件
     */
    private QueryWrapper<SysUser> createQueryWhere(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        Long id = sysUser.getId();
        if (id != null && id > 0) {
            queryWrapper.eq("id", id);
        }
        EnabledStatus status = sysUser.getStatus();
        if (status != null) {
            queryWrapper.eq("status", status.getValue());
        }
        String username = sysUser.getUsername();
        if (StringUtils.isNotEmpty(username)) {
            queryWrapper.eq("username", username);
        }
        String email = sysUser.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            queryWrapper.eq("email", email);
        }
        String phone = sysUser.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            queryWrapper.eq("phone", phone);
        }
        return queryWrapper;
    }
}
