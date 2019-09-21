package com.lee.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.role.domain.SysRole;
import com.lee.role.exception.RoleExistException;

import java.util.List;

/**
 * @author haitao.li
 */
public interface RoleService {
    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 用户拥有的权限
     */
    List<SysRole> findRoleByUserId(Long userId);

    /**
     * 创建权限
     * @param role 权限信息
     * @return 创建条数
     */
    Integer createRole(SysRole role) throws RoleExistException;

    /**
     * 通过权限ID查询权限信息
     * @param id 权限ID
     * @return 权限
     */
    SysRole findRoleById(Long id);

    /**
     * 分页查询权限
     * @param name 权限中文名称,模糊查询
     * @param page 当前页面
     * @param limit 页面数量
     * @return
     */
    IPage<SysRole> findRolePage(String name, Integer page, Integer limit);

    /**
     * 更新权限信息
     * @param role 权限信息
     * @return
     */
    Integer updateRoleById(SysRole role);

    /**
     * 根据code查询角色,code在表中为唯一索引
     * @param code
     * @return
     */
    SysRole finRoleByCode(String code);
}
