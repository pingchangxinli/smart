package com.lee.user.domain;

import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 */
@Data
public class SysUserRequest extends SysUser {
    /**
     * 用户角色集合
     */
    private List<Long> roles;

}
