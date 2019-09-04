package com.lee.admin.service;

import com.lee.admin.domain.Tenant;
import com.lee.admin.mapper.TenantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
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
