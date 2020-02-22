package com.lee.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.api.entity.SysUser;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.core.Pagination;
import com.lee.domain.SysUserRequest;
import com.lee.domain.SysUserVO;

import java.util.List;

/**
 * 参考JdbcUserDetailsManager
 *
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
    IPage<SysUserVO> pageList(Pagination pagination, SysUser sysUser);

    /**
     * 作废用户
     * @param id
     * @return
     */
    Integer disabledUserById(Long id);

    /**
     * 查询用户列表
     *
     * @param sysUser
     * @return
     */
    List<SysUser> findUsers(SysUser sysUser);

    int updateUserById(SysUserRequest sysUserRequest);

    /**
     * 根据用户ID得到用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    SysUser findUserById(Long id);
}
