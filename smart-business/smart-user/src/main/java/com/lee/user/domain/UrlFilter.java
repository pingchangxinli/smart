package com.lee.user.domain;

import com.lee.role.domain.SysRole;
import lombok.Data;

import java.util.List;

/**
 * 配置动态链接访问 链接 角色 权限
 * @author haitao.li
 */
@Data
public class UrlFilter {
    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 链接
     */
    private String url;
    /**
     *
     */
    private List<SysRole> roles;

}
