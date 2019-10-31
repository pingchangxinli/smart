package com.lee.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.business.enums.EnabledStatusEnum;
import com.lee.tenant.domain.CostCenter;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.exception.CostCenterExistedException;
import com.lee.tenant.exception.CostCenterNotExistedException;
import com.lee.tenant.mapper.CostCenterMapper;
import com.lee.tenant.service.CostCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@Service
public class CostCenterServiceImpl implements CostCenterService {
    private static final String ERROR_NOT_EXIST = "系统中不存在该成本中心";
    private static final String ERROR_EXISTED = "系统中已存在该成本中心";
    private static final String ERROR_NEED_ID = "需要ID参数";
    @Resource
    private CostCenterMapper mapper;
    @Override
    public Integer createCostCenter(CostCenter costCenter) throws CostCenterExistedException {
        return mapper.insert(costCenter);
    }

    @Override
    public CostCenter findCostCenterById(Long id) {
        return mapper.selectById(id);
    }


    @Override
    public IPage<CostCenter> pageList(long tenantId, int currentPage, int limit) {
        Page<CostCenter> page = new Page<>(currentPage, limit);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("tenant_id",tenantId);

        IPage<CostCenter> iPage = mapper.selectPage(page,queryWrapper);
        if (log.isDebugEnabled()) {
            log.debug("tenant service page result: {}", iPage);
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

    @Override
    public List<CostCenter> findCostCenter(CostCenter costCenter) {
        QueryWrapper<CostCenter> queryWrapper = new QueryWrapper<>();
        if (costCenter != null) {
            Long id = costCenter.getId();
            if (id != null && id > 0) {
                queryWrapper.eq("id", id);
            }

            String name = costCenter.getName();
            if (StringUtils.isNotEmpty(name)) {
                queryWrapper.like("name", name);
            }

            EnabledStatusEnum status = costCenter.getStatus();

            if (status != null) {
                queryWrapper.eq("status", status.getValue());
            }
        }
        return mapper.selectList(queryWrapper);
    }
}
