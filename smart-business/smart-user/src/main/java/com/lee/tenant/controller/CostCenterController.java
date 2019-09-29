package com.lee.tenant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.EnabledStatus;
import com.lee.common.core.response.BaseResponse;
import com.lee.tenant.domain.CostCenter;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.exception.CostCenterExistedException;
import com.lee.tenant.exception.CostCenterNotExistedException;
import com.lee.tenant.exception.TenantNotExistedException;
import com.lee.tenant.service.CostCenterService;
import com.lee.tenant.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author haitao.li
 */
@RestController
@RequestMapping("/costCenter")
public class CostCenterController {
    private static final Logger logger = LoggerFactory.getLogger(CostCenterController.class);
    private static final String ERROR_TENANT_NOT_FOUND = "未找到租户信息";
    @Resource
    private CostCenterService service;
    @Resource
    private TenantService tenantService;

    /**
     * http://127.0.0.1:8300/user-api/costCenter?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8
     * {"name":"costcenter1","code":"c11"}
     * 新增成本中心
     *
     * @param costCenter 成本中心
     * @return 新增条数
     */
    @PostMapping
    public BaseResponse createCostCenter(@RequestBody CostCenter costCenter,
                                         @RequestHeader("X-TENANT-ID") Long tenantId)
            throws CostCenterExistedException, TenantNotExistedException {
        if (tenantId == null || tenantId.longValue() <= 0) {
            throw new TenantNotExistedException(ERROR_TENANT_NOT_FOUND);
        }
        costCenter.setTenantId(tenantId);
        costCenter.setEnabled(EnabledStatus.ENABLED);
        int count = service.createCostCenter(costCenter);
        return BaseResponse.builder().data(count).build();
    }

    /**
     * http://127.0.0.1:8300/user-api/costCenter?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8
     * {"id":2,"name":"costcenter1","code":"c112"}
     * 根据ID更新成本中心
     *
     * @param costCenter 成本中心
     * @return 更新条数
     * @throws CostCenterNotExistedException 成本中心不存在
     */
    @PutMapping
    public BaseResponse updateCostCenterById(@RequestBody CostCenter costCenter) throws CostCenterNotExistedException {
        int count = service.updateCostCenterById(costCenter);
        return BaseResponse.builder().data(count).build();
    }

    /**
     * http://127.0.0.1:8300/user-api/costCenter/2?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8
     * 根据ID查询出成本中心
     *
     * @param id 成本中心ID
     * @return 成本中心
     */
    @GetMapping("/{id}")
    public BaseResponse findCostCenterById(@PathVariable("id") Long id) {
        CostCenter costCenter = service.findCostCenterById(id);
        return BaseResponse.builder().data(costCenter).build();
    }

    /**
     * http://127.0.0.1:8300/user-api/costCenter/page?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8&page=1&limit=20
     * 分页查询出租户下的成本中心
     *
     * @param tenantId 租户ID
     * @param page 当前页数
     * @param limit 每页条数
     * @return 该分页下的成本中心
     * @throws TenantNotExistedException 租户
     */
    @GetMapping(value = "/page", params = {"page", "limit"})
    public BaseResponse findCostCenterByTenantId(@RequestHeader("X-TENANT-ID") Long tenantId, int page, int limit)
            throws TenantNotExistedException {
        if (tenantId == null || tenantId.longValue() <= 0) {
            throw new TenantNotExistedException(ERROR_TENANT_NOT_FOUND);
        }
        Tenant tenant = tenantService.findTenantById(tenantId);
        IPage<CostCenter> iPage = service.pageList(tenantId, page, limit);
        return BaseResponse.builder().data(iPage).build();
    }
}
