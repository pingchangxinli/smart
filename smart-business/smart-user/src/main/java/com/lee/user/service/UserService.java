package com.lee.user.service;


import com.lee.common.bussiness.domain.LoginUser;
import com.lee.user.domain.SysUser;

/**
 * 参考JdbcUserDetailsManager
 * @author haitao.li
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
}
