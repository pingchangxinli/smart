package com.lee.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.api.entity.SysUser;
import com.lee.domain.SysRole;
import com.lee.api.entity.BusinessUnit;
import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 */
@Data
public class SysUserVO extends SysUser {
    @JsonProperty("cost_center")
    private BusinessUnit businessUnit;
    /**
     * 用户角色集合
     */
    private List<SysRole> roles;
}
