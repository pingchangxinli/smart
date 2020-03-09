package com.lee.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lee.li
 * 角色显示类
 */
@Data
public class SysRoleVO {
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
