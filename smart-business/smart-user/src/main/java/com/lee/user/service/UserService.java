package com.lee.user.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.bussiness.domain.LoginUser;
import com.lee.user.domain.SysUser;

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
    boolean createUser(SysUser user);

    IPage<SysUser> pageList(Integer page, Integer limit, String userCode, String username);

    /**
     * 作废用户
     * @param id
     * @return
     */
    Integer disabledUserById(Long id);
}
