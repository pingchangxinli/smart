package com.lee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.common.core.exception.PageException;
import com.lee.domain.Tenant;
import com.lee.exception.TenantExistedException;
import com.lee.exception.TenantNotExistedException;

import java.util.List;

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
    Tenant findTenantByDomain(String domain);

    /**
     * 按照条件分页查询租户信息
     * @param tenant 住户条件
     * @param pagination 分页条件
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
