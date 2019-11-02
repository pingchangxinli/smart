package com.lee.user.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.core.Pagination;
import com.lee.user.domain.SysUser;
import com.lee.user.domain.SysUserRequest;
import com.lee.user.domain.SysUserResponse;

import java.util.List;

/**
 * 参考JdbcUserDetailsManager
 * @author lee.li
 */
public interface UserService {
    /**
     * 通过username查询用户信息
     * @param username
     * @return
     */
    public SysUser findUserByName(String username);

    /**
     * 内部feign判断登录用户
     * @param username
     * @return
     */
    LoginUser internalFindUserByUserName(String username);

    /**
     * 创建用户
     * @param user
     * @return
     */
    boolean createUser(SysUserRequest user);

    /**
     * 分页查询用户信息
     *
     * @param pagination 分页
     * @param sysUser    用户
     * @return 分页用户信息
     */
    IPage<SysUserResponse> pageList(Pagination pagination, SysUser sysUser);

    /**
     * 作废用户
     * @param id
     * @return
     */
    Integer disabledUserById(Long id);

    /**
     * 查询用户列表
     * @param sysUser
     * @return
     */
    List<SysUser> findUsers(SysUser sysUser);

    int updateUserById(SysUserRequest sysUserRequest);
}
