package com.lee.admin.domain;

import java.time.LocalDateTime;

/**
 * 租户
 * @author haitao.li
 */
public class Tenant {
    private String id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime expiryTime;
    private TenantStatus status;

}
