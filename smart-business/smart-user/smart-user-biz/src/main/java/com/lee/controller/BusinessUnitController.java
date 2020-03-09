package com.lee.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.api.entity.BusinessUnit;
import com.lee.enums.EnabledStatusEnum;
import com.lee.exception.BusinessUnitExistedException;
import com.lee.exception.BusinessUnitNotExistedException;
import com.lee.exception.TenantNotExistedException;
import com.lee.service.BusinessUnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@RestController
@RequestMapping("/businessUnit")
public class BusinessUnitController {
    private static final Long DEFAULT_TENANT_ID = 0L;
    @Resource
    private BusinessUnitService service;

    /**
     * 查询分部列表
     *
     * @param tenantId 租户
     * @return
     */
    @GetMapping("list")
    public BaseResponse<List<BusinessUnit>> findBusinessUnitByTenantId(@RequestHeader("authorization") String authorization,
                                                                       @RequestParam(value = "tenantId",
                                                                               required = false) Long tenantId) {

        if (tenantId == null || tenantId == 0L) {
            tenantId = DEFAULT_TENANT_ID;
        }
        List<BusinessUnit> list = service.findBusinessUnitByTenantId(tenantId);
        return BaseResponse.ok(list);
    }

    /**
     * 新增分部
     *
     * @param businessUnit 分部
     * @return 新增条数
     */
    @PostMapping
    public BaseResponse createBusinessUnit(@RequestBody BusinessUnit businessUnit)
            throws BusinessUnitExistedException {
        businessUnit.setStatus(EnabledStatusEnum.ENABLED);
        int count = service.createBusinessUnit(businessUnit);
        return BaseResponse.ok(count);
    }

    /**
     * 根据ID更新分部
     *
     * @param businessUnit 分部
     * @return 更新条数
     * @throws BusinessUnitNotExistedException 分部不存在
     */
    @PutMapping
    public BaseResponse updateBusinessUnitById(@RequestBody BusinessUnit businessUnit) throws BusinessUnitNotExistedException {
        int count = service.updateBusinessUnitById(businessUnit);
        return BaseResponse.ok(count);
    }

    /**
     * http://127.0.0.1:8300/user-api/BusinessUnit/2?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8
     * 根据ID查询出分部
     *
     * @param id 分部ID
     * @return 分部
     */
    @GetMapping("/{id}")
    public BaseResponse findBusinessUnitById(@PathVariable("id") Long id) {
        BusinessUnit businessUnit = service.findBusinessUnitById(id);
        return BaseResponse.ok(businessUnit);
    }

    /**
     * 分页查询出租户下的分部
     *
     * @return 该分页下的分部
     * @throws TenantNotExistedException 租户
     */
    @GetMapping(value = "/page")
    public BaseResponse pageList(Pagination pagination, BusinessUnit businessUnit) {
        IPage<BusinessUnit> iPage = service.pageList(pagination, businessUnit);
        PaginationResponse response = PaginationResponseUtil.convertIPageToPagination(iPage);
        if (log.isDebugEnabled()) {
            log.debug("[BusinessUnitController pageList] result:" + response);
        }
        return BaseResponse.ok(response);
    }
}
