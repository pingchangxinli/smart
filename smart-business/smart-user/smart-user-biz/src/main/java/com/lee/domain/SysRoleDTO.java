package com.lee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author haitao Li
 */
@Data
public class SysRoleDTO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的:
     */
    private String name;

    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private EnabledStatusEnum status;
    /**
     * 权限 和 角色 是多对多关心
     */
    private List<Permission> permissions;

    private Long tenantId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
