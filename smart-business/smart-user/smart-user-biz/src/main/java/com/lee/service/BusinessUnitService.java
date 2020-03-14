package com.lee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.domain.BusinessUnitDTO;
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
    Integer createBusinessUnit(BusinessUnitDTO businessUnit) throws BusinessUnitExistedException;

    /**
     * 用ID查询分部
     *
     * @param id id
     * @return 分部
     */
    BusinessUnitDTO findBusinessUnitById(Long id);


    /**
     * 根据ID更新分部
     *
     * @param businessUnitDO 分部
     * @return
     * @throws BusinessUnitNotExistedException
     */
    Integer updateBusinessUnitById(BusinessUnitDTO businessUnitDO) throws BusinessUnitNotExistedException;

    /**
     * 分部列表
     *
     * @param businessUnitDO
     * @return
     */
    List<BusinessUnitDTO> findBusinessUnit(BusinessUnitDTO businessUnitDO);

    /**
     * 分部列表
     *
     * @param tenantId 租户ID
     * @return
     */
    List<BusinessUnitDTO> findBusinessUnitByTenantId(Long tenantId);

    /**
     * 分页查询分部
     *
     * @param pagination
     * @param businessUnitDO
     * @return
     */
    IPage<BusinessUnitDTO> pageList(Pagination pagination, BusinessUnitDTO businessUnitDO);
}
