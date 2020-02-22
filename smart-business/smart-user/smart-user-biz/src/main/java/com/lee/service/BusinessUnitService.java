package com.lee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.api.entity.BusinessUnit;
import com.lee.exception.BusinessUnitExistedException;
import com.lee.exception.BusinessUnitNotExistedException;

import java.util.List;

/**
 * @author lee.li
 */
public interface BusinessUnitService {
    /**
     * 新增分部
     *
     * @param businessUnit 分部
     * @return
     */
    Integer createBusinessUnit(BusinessUnit businessUnit) throws BusinessUnitExistedException;

    /**
     * 用ID查询分部
     *
     * @param id id
     * @return 分部
     */
    BusinessUnit findBusinessUnitById(Long id);


    /**
     * 根据ID更新分部
     *
     * @param businessUnit 分部
     * @return
     * @throws BusinessUnitNotExistedException
     */
    Integer updateBusinessUnitById(BusinessUnit businessUnit) throws BusinessUnitNotExistedException;

    /**
     * 分部列表
     *
     * @param businessUnit
     * @return
     */
    List<BusinessUnit> findBusinessUnit(BusinessUnit businessUnit);

    /**
     * 分部列表
     *
     * @param tenantId 租户ID
     * @return
     */
    List<BusinessUnit> findBusinessUnitByTenantId(Long tenantId);

    /**
     * 分页查询分部
     *
     * @param pagination
     * @param businessUnit
     * @return
     */
    IPage<BusinessUnit> pageList(Pagination pagination, BusinessUnit businessUnit);
}
