package com.lee.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author haitao.li
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TenantServiceImpl implements TenantService {
    @Autowired
    private TenantMapper tenantMapper;
    @Override
    public List<Tenant> list() {
        return tenantMapper.list();
    }
}
