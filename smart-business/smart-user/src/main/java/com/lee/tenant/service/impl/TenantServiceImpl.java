package com.lee.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.business.EnabledStatus;
import com.lee.common.core.exception.PageException;
import com.lee.tenant.TenantErrorEnum;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.exception.TenantExistedException;
import com.lee.tenant.exception.TenantNotExistedException;
import com.lee.tenant.mapper.TenantMapper;
import com.lee.tenant.service.TenantService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haitao.li
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TenantServiceImpl implements TenantService {
    private static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);

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
    public Integer createTenant(Tenant tenant) throws TenantExistedException, TenantNotExistedException {
        String name = tenant.getName();
        String domain = tenant.getDomain();
        if (StringUtils.isEmpty(domain) || StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException(ERROR_DOMAIN_NAME);
        }
        Tenant temp = this.findTenantByDomain(tenant.getDomain());
        if (ObjectUtils.isNotEmpty(temp)) {
            TenantErrorEnum error = TenantErrorEnum.EXISTED;
            throw new TenantExistedException(error.getErrorDes());
        }
        int count = tenantMapper.insert(tenant);
        return count;
    }

    @Override
    public Tenant findTenantById(Long id) throws TenantNotExistedException {
        String key = KEY_TENANT_PRE + id;
        Tenant tenantInRedis = (Tenant) redisTemplate.opsForValue().get(key);
        if (logger.isDebugEnabled()) {
            logger.debug("tenant in redis is null ? " + ObjectUtils.isEmpty(tenantInRedis));
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
    public Tenant findTenantByDomain(String domain) throws TenantNotExistedException {
        Wrapper wrapper = new QueryWrapper<Tenant>().lambda().eq(Tenant::getDomain, domain);
        Tenant tenant = tenantMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(tenant)) {
            TenantErrorEnum errorEnum = TenantErrorEnum.NOT_EXISTED;
            throw new TenantNotExistedException(errorEnum.getErrorDes());
        }
        return tenant;
    }

    @Override
    public IPage<Tenant> pageList(String name,Integer currentPage,Integer limit) throws PageException {

        Page<Tenant> page = new Page<>(currentPage, limit);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("name",name);

        IPage<Tenant> iPage = tenantMapper.selectPage(page,queryWrapper);
        if (logger.isDebugEnabled()) {
            logger.debug("tenant service page result: {}", iPage);
        }
        return iPage;

    }

    @Override
    public Integer updateTenantById(Tenant tenant) {

        return tenantMapper.updateById(tenant);
    }

}
