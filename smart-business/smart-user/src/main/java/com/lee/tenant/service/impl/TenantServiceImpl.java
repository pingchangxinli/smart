package com.lee.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.common.core.exception.SmartException;
import com.lee.tenant.TenantErrorEnum;
import com.lee.tenant.TenantExistedException;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.mapper.TenantMapper;
import com.lee.tenant.service.TenantService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
    private static final String ERROR_DOMAIN_NAME = "域名或者名称缺失";
    @Resource
    private  TenantMapper tenantMapper;

    @Override
    public List<Tenant> list() {
        return tenantMapper.selectList(null);
    }

    @Override
    public Integer createTenant(Tenant tenant) throws TenantExistedException {
        String name = tenant.getName();
        String domain = tenant.getDomain();
        if(StringUtils.isEmpty(domain) || StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException(ERROR_DOMAIN_NAME);
        }
        Tenant temp = this.findTenantByDomain(tenant.getDomain());
        if (ObjectUtils.isNotEmpty(temp)) {
            TenantErrorEnum error = TenantErrorEnum.EXISTED;
            throw new TenantExistedException(error.getErrorDes());
        }
        return tenantMapper.insert(tenant);
    }

    @Override
    public Tenant findTenantById(String id) {
        return tenantMapper.selectById(id);
    }

    @Override
    public Tenant findTenantByDomain(String domain) {
        Wrapper wrapper = new QueryWrapper<Tenant>().lambda().eq(Tenant::getDomain,domain);
        return tenantMapper.selectOne(wrapper);
    }

}
