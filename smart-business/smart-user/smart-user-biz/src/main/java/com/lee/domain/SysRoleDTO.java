package com.lee.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lee.li
 */
@Data
public class SysRoleDTO {
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
