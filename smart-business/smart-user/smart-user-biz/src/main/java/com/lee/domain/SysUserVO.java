package com.lee.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.api.entity.SysUser;
import com.lee.api.entity.BusinessUnit;
import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 */
@Data
public class SysUserVO extends SysUser {
    @JsonProperty("business_unit")
    private BusinessUnit businessUnit;
    /**
     * 用户角色集合
     */
    private List<SysRoleDO> roles;
}
