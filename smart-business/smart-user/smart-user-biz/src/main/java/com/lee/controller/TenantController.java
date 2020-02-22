package com.lee.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.core.Pagination;
import com.lee.common.core.exception.PageException;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.util.JsonUtil;
import com.lee.enums.EnabledStatus;
import com.lee.enums.TenantErrorEnum;
import com.lee.domain.Tenant;
import com.lee.exception.TenantExistedException;
import com.lee.exception.TenantNotExistedException;
import com.lee.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 租户管理
 *
 * @author lee.li
 */
@Slf4j
@RestController
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    private HttpServletRequest request;
    @Resource
    private TenantService tenantService;


    /**
     * 新增租户
     * @param tenant 租户
     * @return
     */
    @PostMapping
    public BaseResponse createTenant(@RequestBody Tenant tenant) throws TenantExistedException {
        if (StringUtils.isEmpty(tenant.getDomain())) {
            throw new IllegalArgumentException(TenantErrorEnum.DOMAIN_PARAM_NOT_EXISTED.getErrorDes());
        }
        tenant.setStatus(EnabledStatus.ENABLED);
        LocalDateTime localDateTime = LocalDateTime.now();
        tenant.setCreateTime(localDateTime);
        tenant.setUpdateTime(localDateTime);
        Tenant tenant1 = tenantService.findTenantByDomain(tenant.getDomain());
        if (tenant1 != null) {
            return BaseResponse.error(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    TenantErrorEnum.EXISTED.getErrorDes());
        } else {
            Integer count = tenantService.createTenant(tenant);
            return BaseResponse.ok(count);
        }
    }

    /**
     * 更新租户信息
     * @param tenant 租户信息
     * @return
     */
    @PutMapping
    public BaseResponse updateTenantById(@RequestBody Tenant tenant) {
        if (log.isDebugEnabled()) {
            log.debug("update Tenant,request tenant: {}", tenant);
        }
        Integer count = tenantService.updateTenantById(tenant);
        return BaseResponse.ok(count);
    }
    /**
     * 分页查询租户信息
     * @param pagination 分页信息
     * @param tenant 查询条件
     * @return 分页查询结果
     */
    @GetMapping(value = "/page")
    public BaseResponse pageList(Pagination pagination, Tenant tenant) {
        IPage<Tenant> list = null;
        try {
            list = tenantService.pageList(tenant, pagination);
        } catch (PageException e) {
            log.error("query tenant page failed", e);
        }
        if (log.isDebugEnabled()) {
            try {
                log.debug("[TenantController pageList],response:" + JsonUtil.toJson(list));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        PaginationResponse<Tenant> paginationResponse = PaginationResponseUtil.convertIPageToPagination(list);
        return BaseResponse.ok(paginationResponse);

    }

    /**
     * 通过ID查询租户
     * @param id 租户ID
     * @return 租户信息
     */
    @GetMapping("/{id}")
    public BaseResponse findTenantById(@PathVariable("id") String id) {
        Tenant tenant = null;
        try {
            tenant = tenantService.findTenantById(Long.valueOf(id));
        } catch (TenantNotExistedException e) {
            log.info("tenant id:" + id + " not exist");
        }
        return BaseResponse.ok(tenant);
    }

    /**
     * http://127.0.0.1:8300/user-api/tenant/domain?domain=127.0.0.1&access_token=f855e6e3-ec83-4014-add3-db83f07f9fcd
     * 根据域名获取租户信息
     *
     * @param domain
     * @return
     */
    @GetMapping("/domain")
    public BaseResponse findTenantByDomain(@RequestParam("domain") String domain) {
        Tenant tenant = tenantService.findTenantByDomain(domain);
        return BaseResponse.ok(tenant);
    }

    /**
     * http://127.0.0.1:8300/user-api/tenant/domain/id?domain=127.0.0
     * .1&access_token=f855e6e3-ec83-4014-add3-db83f07f9fcd
     * 根据域名获取租户I
     * @param domain
     * @return
     */
    @GetMapping("/domain/id")
    public BaseResponse findTenantIdByDomain(@RequestParam("domain") String domain) {
        Tenant tenant = tenantService.findTenantByDomain(domain);
        return BaseResponse.ok(tenant.getId());
    }
}
