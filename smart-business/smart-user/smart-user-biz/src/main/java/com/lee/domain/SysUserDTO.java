package com.lee.domain;

import com.lee.api.vo.SysRoleVO;
import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * @author haitao Li
 * service 用户传输对象
 */
@Data
public class SysUserDTO {
    /**
     * 自生成ID
     **/
    private Long id;
    /**
     * 中文名称
     */
    private String chineseName;
    /**
     * 用户名
     **/
    private String username;
    /**
     * 密码
     **/
    private String password;
    /**
     * 邮箱
     **/
    private String email;
    /**
     * 手机号
     **/
    private String phone;
    /**
     * 租户ID
     **/
    private Long tenantId;
    /**
     * 客户分部编号
     **/
    private Long businessUnitId;

    /**
     * 是否可用
     **/
    private EnabledStatusEnum status;

    private List<SysRoleDTO> roles;
}
