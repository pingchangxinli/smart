package com.lee.tenant.service;

import com.lee.common.core.exception.SmartException;
import com.lee.tenant.TenantExistedException;
import com.lee.tenant.domain.Tenant;

import java.util.List;

/**
 * @author haitao.li
 */
public interface TenantService {
    /**
     * 查询所有的租户
     * @return 租户列表
     */
    List<Tenant> list();

    /**
     * 添加新租户
     * @param tenant 租户
     * @return 添加条数
     */
    Integer createTenant(Tenant tenant) throws TenantExistedException;

    /**
     *  查询租户信息
     * @param id 租户ID
     * @return 租户信息
     */
    Tenant findTenantById(String id);

    /**
     * 通过域名查询租户信息
     * @param domain 域名
     * @return 租户信息
     */
    Tenant findTenantByDomain(String domain);

}
