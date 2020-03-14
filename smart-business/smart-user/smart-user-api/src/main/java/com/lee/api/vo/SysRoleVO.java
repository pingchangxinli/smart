package com.lee.api.vo;

import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lee.li
 * 角色显示类
 */
@Data
public class SysRoleVO {
    private Long id;
    private String name;
    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private EnabledStatusEnum status;

    private Long tenantId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
