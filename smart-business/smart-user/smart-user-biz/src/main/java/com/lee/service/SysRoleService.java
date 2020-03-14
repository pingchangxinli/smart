package com.lee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.domain.SysRoleDTO;
import com.lee.enums.EnabledStatusEnum;
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
    List<SysRoleDTO> findRoleByUserId(Long userId);

    /**
     * 创建权限
     *
     * @param sysRoleDTO 权限信息
     * @return 创建条数
     */
    Integer createRole(SysRoleDTO sysRoleDTO) throws RoleExistException;

    /**
     * 通过权限ID查询权限信息
     *
     * @param id 权限ID
     * @return 权限
     */
    SysRoleDTO findRoleById(Long id);

    /**
     * 分页查询权限
     *
     * @param pagination 分页
     * @param sysRoleDTO 角色类
     * @return
     */
    IPage<SysRoleDTO> findRolePage(Pagination pagination, SysRoleDTO sysRoleDTO);

    /**
     * 更新权限信息
     *
     * @param role 权限信息
     * @return
     */
    Integer updateRoleById(SysRoleDTO role);

    /**
     * 根据code查询角色,code在表中为唯一索引
     *
     * @param name 名称
     * @return
     */
    SysRoleDTO finRoleByName(String name);

    /**
     * 查询多个 role id 下的 role
     *
     * @param list
     * @return
     */
    List<SysRoleDTO> findRoleByIdList(List<Long> list);

    /**
     * 租户下的所有权限
     *
     * @return 权限列表
     */
    List<SysRoleDTO> findAllRoles();

    /**
     * 根据状态返回角色信息
     *
     * @param status 角色状态
     * @return 角色信息
     */
    List<SysRoleDTO> findRolesByStatus(EnabledStatusEnum status);
}
