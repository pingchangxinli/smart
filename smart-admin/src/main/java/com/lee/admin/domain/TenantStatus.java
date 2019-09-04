package com.lee.admin.domain;

/**
 * 租户状态
 * @author haitao.li
 */
public enum TenantStatus {
    /**
     * 启用
     */
    ENABLED("启用"),
    /**
     * 失效
     */
    DISABLED("失效");
    /**
     * 描述
     */
    private String description;
    TenantStatus(String description) {
        this.description = description;
    }
}
