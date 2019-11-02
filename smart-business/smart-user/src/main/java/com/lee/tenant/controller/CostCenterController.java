package com.lee.tenant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.EnabledStatus;
import com.lee.common.business.util.IPageToPaginationResponse;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.tenant.domain.CostCenter;
import com.lee.tenant.exception.CostCenterExistedException;
import com.lee.tenant.exception.CostCenterNotExistedException;
import com.lee.tenant.exception.TenantNotExistedException;
import com.lee.tenant.service.CostCenterService;
import com.lee.tenant.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@RestController
@RequestMapping("/costCenter")
public class CostCenterController {
    @Resource
    private CostCenterService service;
    @Resource
    private TenantService tenantService;
    @Resource
    private HttpServletRequest request;

    /**
     * 查询成本中心列表
     *
     * @param costCenter
     * @return
     */
    @GetMapping
    public BaseResponse findCostCenterByTenantId(CostCenter costCenter) {
        if (log.isDebugEnabled()) {
            log.debug("[CostCenterController.findCostCenter]  request params: " + costCenter);
        }
        List<CostCenter> list = service.findCostCenter(costCenter);
        return BaseResponse.builder().data(list).build();
    }
    /**
     * http://127.0.0.1:8300/user-api/costCenter?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8
     * {"name":"costcenter1"}
     * 新增成本中心
     *
     * @param costCenter 成本中心
     * @return 新增条数
     */
    @PostMapping
    public BaseResponse createCostCenter(@RequestBody CostCenter costCenter)
            throws CostCenterExistedException {
        costCenter.setStatus(EnabledStatus.ENABLED);
        int count = service.createCostCenter(costCenter);
        return BaseResponse.builder().data(count).build();
    }

    /**
     * 根据ID更新成本中心
     *
     * @param costCenter 成本中心
     * @return 更新条数
     * @throws CostCenterNotExistedException 成本中心不存在
     */
    @PutMapping
    public BaseResponse updateCostCenterById(@RequestBody CostCenter costCenter) throws CostCenterNotExistedException {
        int count = service.updateCostCenterById(costCenter);
        HttpStatus httpStatus = HttpStatus.OK;
        return BaseResponse.builder().data(count).subCode(String.valueOf(httpStatus.value()))
                .subMsg(httpStatus.getReasonPhrase()).build();
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
     * 分页查询出租户下的成本中心
     *
     * @return 该分页下的成本中心
     * @throws TenantNotExistedException 租户
     */
    @GetMapping(value = "/page")
    public BaseResponse pageList(Pagination pagination, CostCenter costCenter) {
        IPage<CostCenter> iPage = service.pageList(pagination, costCenter);
        PaginationResponse response = IPageToPaginationResponse.convertIPageToPagination(iPage);
        if (log.isDebugEnabled()) {
            log.debug("[CostCenterController pageList] result:" + response);
        }
        return BaseResponse.builder().data(response).build();
    }
}
