package com.lee.user.service;

import com.lee.user.domain.SysRole;

import java.util.List;

/**
 * @author haitao.li
 */
public interface RoleService {
    List<SysRole> findRoleByUserId(Long userId);
}
