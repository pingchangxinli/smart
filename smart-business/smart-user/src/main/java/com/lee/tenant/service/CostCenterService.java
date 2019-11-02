package com.lee.tenant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.tenant.domain.CostCenter;
import com.lee.tenant.exception.CostCenterExistedException;
import com.lee.tenant.exception.CostCenterNotExistedException;

import java.util.List;

/**
 * @author lee.li
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
     * 根据ID更新成本中心
     * @param costCenter 成本中心
     * @return
     * @throws CostCenterNotExistedException
     */
    Integer updateCostCenterById(CostCenter costCenter) throws CostCenterNotExistedException;

    /**
     * 成本中心列表
     *
     * @param costCenter
     * @return
     */
    List<CostCenter> findCostCenter(CostCenter costCenter);

    /**
     * 分页查询成本中心
     *
     * @param pagination
     * @param costCenter
     * @return
     */
    IPage<CostCenter> pageList(Pagination pagination, CostCenter costCenter);
}
