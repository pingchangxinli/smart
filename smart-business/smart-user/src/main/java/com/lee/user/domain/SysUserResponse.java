package com.lee.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.role.domain.SysRole;
import com.lee.tenant.domain.CostCenter;
import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 */
@Data
public class SysUserResponse extends SysUser {
    @JsonProperty("cost_center")
    private CostCenter costCenter;
    /**
     * 用户角色集合
     */
    private List<SysRole> roles;
}
