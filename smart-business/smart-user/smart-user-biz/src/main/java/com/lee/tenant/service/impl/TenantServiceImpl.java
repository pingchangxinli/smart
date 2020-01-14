package com.lee.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.business.EnabledStatus;
import com.lee.common.core.Pagination;
import com.lee.common.core.exception.PageException;
import com.lee.tenant.TenantErrorEnum;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.exception.TenantExistedException;
import com.lee.tenant.exception.TenantNotExistedException;
import com.lee.tenant.mapper.TenantMapper;
import com.lee.tenant.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TenantServiceImpl implements TenantService {

    private static final String ERROR_DOMAIN_NAME = "域名或者名称缺失";

    private static final String ERROR_PAGE = "分页查询当前页面参数page或者每页数量参数limit不正确";
    /**
     * redis 存储 tenant key 前缀
     */
    private static final String KEY_TENANT_PRE = "tenant:";
    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<Tenant> list() {
        return tenantMapper.selectList(null);
    }

    @Override
    public List<Tenant> list(Tenant tenant) {
        QueryWrapper<Tenant> wrapper = new QueryWrapper<>();

        String domain = tenant.getDomain();
        if (StringUtils.isNotEmpty(domain)) {
            wrapper.like("domain",domain);
        }
        EnabledStatus status = tenant.getStatus();
        if (status != null) {
            wrapper.eq("status",status);
        }

        String name = tenant.getName();
        if (StringUtils.isNotEmpty(name)) {
            wrapper.like("name",name);
        }
        return tenantMapper.selectList(wrapper);
    }

    @Override
    public Integer createTenant(Tenant tenant) throws TenantExistedException {
        if (log.isDebugEnabled()) {
            log.debug("[Tenant service] createTenant,params:"+tenant);
        }
        String domain = tenant.getDomain();
        if (StringUtils.isEmpty(domain)) {
            throw new IllegalArgumentException(TenantErrorEnum.DOMAIN_PARAM_NOT_EXISTED.getErrorDes());
        }
        Tenant temp = this.findTenantByDomain(domain);
        if (ObjectUtils.isNotEmpty(temp)) {
            throw new TenantExistedException(TenantErrorEnum.EXISTED.getErrorDes());
        }
        return tenantMapper.insert(tenant);
    }

    @Override
    public Tenant findTenantById(Long id) throws TenantNotExistedException {
        String key = KEY_TENANT_PRE + id;
        Tenant tenantInRedis = (Tenant) redisTemplate.opsForValue().get(key);
        if (log.isDebugEnabled()) {
            log.debug("tenant in redis is null ? " + ObjectUtils.isEmpty(tenantInRedis));
        }
        if (ObjectUtils.isEmpty(tenantInRedis)) {
            Tenant tenant = tenantMapper.selectById(id);
            if (ObjectUtils.isEmpty(tenant)) {
                TenantErrorEnum error = TenantErrorEnum.NOT_EXISTED;
                throw new TenantNotExistedException(error.getErrorDes());
            } else {
                redisTemplate.opsForValue().set(key, tenant);
            }
            return tenant;
        } else {
            return tenantInRedis;
        }

    }

    @Override
    public Tenant findTenantByDomain(String domain) {
        Wrapper wrapper = new QueryWrapper<Tenant>().lambda().eq(Tenant::getDomain, domain);
        Tenant tenant = tenantMapper.selectOne(wrapper);
        return tenant;
    }

    @Override
    public IPage<Tenant> pageList(Tenant tenant, Pagination pagination) throws PageException {

        Page<Tenant> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());

        QueryWrapper queryWrapper = new QueryWrapper();
        String name = tenant.getName();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        String domain = tenant.getDomain();
        if (StringUtils.isNotEmpty(domain)) {
            queryWrapper.like("domain", domain);
        }
        EnabledStatus status = tenant.getStatus();
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        IPage<Tenant> iPage = tenantMapper.selectPage(page,queryWrapper);
        if (log.isDebugEnabled()) {
            log.debug("tenant service page result: {}", iPage);
        }
        return iPage;

    }

    @Override
    public Integer updateTenantById(Tenant tenant) {

        return tenantMapper.updateById(tenant);
    }

}
