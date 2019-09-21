package com.lee.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.tenant.domain.CostCenter;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.exception.CostCenterExistedException;
import com.lee.tenant.exception.CostCenterNotExistedException;
import com.lee.tenant.mapper.CostCenterMapper;
import com.lee.tenant.service.CostCenterService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author haitao.li
 */
@Service
public class CostCenterServiceImpl implements CostCenterService {
    private static final Logger logger = LoggerFactory.getLogger(CostCenterServiceImpl.class);
    private static final String ERROR_NOT_EXIST = "系统中不存在该成本中心";
    private static final String ERROR_EXISTED = "系统中已存在该成本中心";
    private static final String ERROR_NEED_ID = "需要ID参数";
    @Resource
    private CostCenterMapper mapper;
    @Override
    public Integer createCostCenter(CostCenter costCenter) throws CostCenterExistedException {
        CostCenter temp = this.findCostCenterByCode(costCenter.getCode(),costCenter.getTenantId());
        if(ObjectUtils.isNotEmpty(temp)) {
            throw new CostCenterExistedException(ERROR_EXISTED);
        }
        return mapper.insert(costCenter);
    }

    @Override
    public CostCenter findCostCenterById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public CostCenter findCostCenterByCode(String code, Long tenantId) {
        QueryWrapper<CostCenter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",code);
        queryWrapper.eq("tenant_id",tenantId);
        return mapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<CostCenter> pageList(long tenantId, int currentPage, int limit) {
        Page<CostCenter> page = new Page<>(currentPage, limit);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("tenant_id",tenantId);

        IPage<CostCenter> iPage = mapper.selectPage(page,queryWrapper);
        if (logger.isDebugEnabled()) {
            logger.debug("tenant service page result: {}", iPage);
        }
        return iPage;
    }

    @Override
    public Integer updateCostCenterById(CostCenter costCenter) throws CostCenterNotExistedException {
        Long id = costCenter.getId();
        if( id == null || id.longValue() <= 0 ) {
            throw new IllegalArgumentException(ERROR_NEED_ID);
        }
        CostCenter temp = this.findCostCenterById(id);
        if (ObjectUtils.isEmpty(temp)) {
            throw new CostCenterNotExistedException(ERROR_NOT_EXIST);
        }
        return mapper.updateById(costCenter);
    }
}
