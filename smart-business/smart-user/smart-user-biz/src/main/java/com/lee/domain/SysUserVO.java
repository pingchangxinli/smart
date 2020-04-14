package com.lee.domain;

import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * @author haitao Li
 */
@Data
public class SysUserVO {
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
    /**
     * 用户角色集合
     */
    private List<Long> roles;

}
