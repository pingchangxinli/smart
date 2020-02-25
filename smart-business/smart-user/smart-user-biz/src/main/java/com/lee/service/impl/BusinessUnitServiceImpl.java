package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.core.Pagination;
import com.lee.api.entity.BusinessUnit;
import com.lee.enums.EnabledStatusEnum;
import com.lee.exception.BusinessUnitExistedException;
import com.lee.exception.BusinessUnitNotExistedException;
import com.lee.mapper.BusinessUnitMapper;
import com.lee.service.BusinessUnitService;
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
public class BusinessUnitServiceImpl implements BusinessUnitService {
    private static final String ERROR_NOT_EXIST = "系统中不存在该分部";
    private static final String ERROR_EXISTED = "系统中已存在该分部";
    private static final String ERROR_NEED_ID = "需要ID参数";
    @Resource
    private BusinessUnitMapper mapper;

    @Override
    public Integer createBusinessUnit(BusinessUnit businessUnit) throws BusinessUnitExistedException {
        return mapper.insert(businessUnit);
    }

    @Override
    public BusinessUnit findBusinessUnitById(Long id) {
        return mapper.selectById(id);
    }


    @Override
    public IPage<BusinessUnit> pageList(Pagination pagination, BusinessUnit businessUnit) {
        Page<BusinessUnit> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());

        QueryWrapper queryWrapper = new QueryWrapper();

        String name = businessUnit.getName();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        EnabledStatusEnum status = businessUnit.getStatus();
        if (status != null) {
            queryWrapper.eq("status", status.getValue());
        }
        IPage<BusinessUnit> iPage = mapper.selectPage(page, queryWrapper);

        return iPage;
    }

    @Override
    public Integer updateBusinessUnitById(BusinessUnit businessUnit) throws BusinessUnitNotExistedException {
        Long id = businessUnit.getId();
        if (id == null || id.longValue() <= 0) {
            throw new IllegalArgumentException(ERROR_NEED_ID);
        }
        BusinessUnit temp = this.findBusinessUnitById(id);
        if (ObjectUtils.isEmpty(temp)) {
            throw new BusinessUnitNotExistedException(ERROR_NOT_EXIST);
        }
        return mapper.updateById(businessUnit);
    }

    @Override
    public List<BusinessUnit> findBusinessUnit(BusinessUnit businessUnit) {
        QueryWrapper<BusinessUnit> queryWrapper = new QueryWrapper<>();
        if (businessUnit != null) {
            Long id = businessUnit.getId();
            if (id != null && id > 0) {
                queryWrapper.eq("id", id);
            }

            String name = businessUnit.getName();
            if (StringUtils.isNotEmpty(name)) {
                queryWrapper.like("name", name);
            }

            EnabledStatusEnum status = businessUnit.getStatus();

            if (status != null) {
                queryWrapper.eq("status", status.getValue());
            }
        }
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<BusinessUnit> findBusinessUnitByTenantId(Long tenantId) {
        LambdaQueryWrapper<BusinessUnit> queryWrapper = new QueryWrapper<BusinessUnit>().lambda()
                .eq(BusinessUnit::getTenantId, tenantId);
        return mapper.selectList(queryWrapper);
    }
}
