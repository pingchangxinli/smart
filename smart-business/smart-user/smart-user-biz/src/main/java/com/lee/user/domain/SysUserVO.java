package com.lee.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.api.entity.SysUser;
import com.lee.role.domain.SysRole;
import com.lee.tenant.domain.Shop;
import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 */
@Data
public class SysUserVO extends SysUser {
    @JsonProperty("cost_center")
    private Shop shop;
    /**
     * 用户角色集合
     */
    private List<SysRole> roles;
}
