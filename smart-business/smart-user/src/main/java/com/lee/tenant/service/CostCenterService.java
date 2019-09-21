package com.lee.tenant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.tenant.domain.CostCenter;
import com.lee.tenant.exception.CostCenterExistedException;
import com.lee.tenant.exception.CostCenterNotExistedException;

/**
 * @author haitao.li
 */
public interface CostCenterService {
    /**
     * 新增成本中心
     * @param costCenter 成本中心
     * @return
     */
    Integer createCostCenter(CostCenter costCenter) throws CostCenterExistedException;

    /**
     * 用ID查询成本中心
     * @param id id
     * @return 成本中心
     */
    CostCenter findCostCenterById(Long id);

    /**
     * 租户自定义code查询成本中心
     * @param code 自定义code
     * @param tenantId 租户ID
     * @return 成本中心
     */
    CostCenter findCostCenterByCode(String code,Long tenantId);

    /**
     * 分页查询成本中心
     * @param tenantId 租户Id
     * @param page 当前页码
     * @param limit 每页条数
     * @return
     */
    IPage<CostCenter> pageList(long tenantId, int page, int limit);

    /**
     * 根据ID更新成本中心
     * @param costCenter 成本中心
     * @return
     * @throws CostCenterNotExistedException
     */
    Integer updateCostCenterById(CostCenter costCenter) throws CostCenterNotExistedException;
}
