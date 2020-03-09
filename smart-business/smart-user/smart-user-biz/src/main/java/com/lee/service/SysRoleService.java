package com.lee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.domain.SysRoleDO;
import com.lee.domain.SysRoleDTO;
import com.lee.exception.RoleExistException;

import java.util.List;

/**
 * @author lee.li
 */
public interface SysRoleService {
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 用户拥有的权限
     */
    List<SysRoleDO> findRoleByUserId(Long userId);

    /**
     * 创建权限
     *
     * @param SysRoleDTO 权限信息
     * @return 创建条数
     */
    Integer createRole(SysRoleDTO sysRoleDTO) throws RoleExistException;

    /**
     * 通过权限ID查询权限信息
     *
     * @param id 权限ID
     * @return 权限
     */
    SysRoleDO findRoleById(Long id);

    /**
     * 分页查询权限
     *
     * @param pagination 分页
     * @param SysRoleDTO 角色类
     * @return
     */
    IPage<SysRoleDO> findRolePage(Pagination pagination, SysRoleDTO sysRole);

    /**
     * 更新权限信息
     *
     * @param role 权限信息
     * @return
     */
    Integer updateRoleById(SysRoleDO role);

    /**
     * 根据code查询角色,code在表中为唯一索引
     *
     * @param name 名称
     * @return
     */
    SysRoleDO finRoleByName(String name);

    /**
     * 查询多个 role id 下的 role
     *
     * @param list
     * @return
     */
    List<SysRoleDO> findRoleByIdList(List<Long> list);

    /**
     * 租户下的所有权限
     * @return 权限列表
     */
    List<SysRoleDO> findAllRoles();
}
