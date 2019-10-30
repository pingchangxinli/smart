package com.lee.tenant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.EnabledStatus;
import com.lee.common.core.Pagination;
import com.lee.common.core.exception.PageException;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.exception.TenantExistedException;
import com.lee.tenant.exception.TenantNotExistedException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author lee.li
 */
public interface TenantService {
    /**
     * 查询所有的租户
     *
     * @return 租户列表
     */
    List<Tenant> list();

    List<Tenant> list(Tenant tenant);
    /**
     * 添加新租户
     *
     * @param tenant 租户
     * @return 添加条数
     */
    Integer createTenant(Tenant tenant) throws TenantExistedException;

    /**
     * 查询租户信息
     *
     * @param id 租户ID
     * @return 租户信息
     */
    Tenant findTenantById(Long id) throws TenantNotExistedException;

    /**
     * 通过域名查询租户信息
     *
     * @param domain 域名
     * @return 租户信息
     */
    Tenant findTenantByDomain(String domain) throws TenantNotExistedException;

    /**
     * 按照条件分页查询租户信息
     * @param name 租户名称
     * @param page 当前页数
     * @param limit 每页条数
     * @return
     * @throws PageException
     */
    IPage<Tenant> pageList(Tenant tenant, Pagination pagination) throws PageException;

    /**
     * 修改租户信息
     * @param tenant 租户信息
     * @return 更新条数
     */
    Integer updateTenantById(Tenant tenant);


}
