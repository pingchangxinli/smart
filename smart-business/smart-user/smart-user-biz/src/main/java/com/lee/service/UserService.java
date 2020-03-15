package com.lee.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.api.vo.SysUserVO;
import com.lee.domain.SysUserDO;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.core.Pagination;
import com.lee.domain.SysUserDTO;

import java.util.List;

/**
 * 参考JdbcUserDetailsManager
 *
 * @author lee.li
 */
public interface UserService {
    /**
     * 通过username查询用户信息
     *
     * @param username
     * @return
     */
    public SysUserDO findUserByName(String username);

    /**
     * 内部feign判断登录用户
     * @param username
     * @return
     */
    LoginUser internalFindUserByUserName(String username);

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    boolean createUser(SysUserDTO user);

    /**
     * 分页查询用户信息
     *
     * @param pagination 分页
     * @param sysUserDO  用户
     * @return 分页用户信息
     */
    IPage<SysUserVO> pageList(Pagination pagination, SysUserDO sysUserDO);

    /**
     * 作废用户
     * @param id
     * @return
     */
    Integer disabledUserById(Long id);

    /**
     * 查询用户列表
     *
     * @param sysUserDTO
     * @return
     */
    List<SysUserDO> findUsers(SysUserDTO sysUserDTO);

    int updateUserById(SysUserDTO sysUserRequest);

    /**
     * 根据用户ID得到用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    SysUserDTO findUserById(Long id);
}
