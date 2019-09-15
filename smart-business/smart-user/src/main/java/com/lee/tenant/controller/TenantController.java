package com.lee.tenant.controller;


import com.lee.common.core.response.BaseResponse;
import com.lee.tenant.TenantExistedException;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 租户管理
 *
 * @author haitao.li
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {
    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);
    @Autowired
    private HttpServletRequest request;
    @Resource
    private TenantService tenantService;


    /**
     * 新增租户
     *
     * @param tenant 租户
     * @return
     */
    @PostMapping
    public BaseResponse createTenant(@RequestBody Tenant tenant) {
        if (logger.isDebugEnabled()) {
            logger.debug("create Tenant,request tenant: {}", tenant);
        }
        Integer count = null;
        try {
            count = tenantService.createTenant(tenant);
        } catch (TenantExistedException e) {
            logger.info("create tenant failed,tenant name:{},domain:{}", tenant.getName(), tenant.getDomain());
        }
        return BaseResponse.builder().data(count).build();
    }

    /**
     * 查询所有租户
     *
     * @return
     */
    @GetMapping("/list")
    public BaseResponse list() {
        List<Tenant> list = tenantService.list();
        return BaseResponse.builder().data(list).build();
    }

    /**
     * 通过ID查询出租户
     *
     * @param id 租户ID
     * @return 租户信息
     */
    @GetMapping("/{id}")
    public BaseResponse findTenantById(@PathVariable("id") String id) {
        Tenant tenant = tenantService.findTenantById(id);
        return BaseResponse.builder().data(tenant).build();
    }

    /**
     * 根据域名获取租户信息
     *
     * @param domain
     * @return
     */
    @GetMapping("/domain")
    public BaseResponse findTenantByDomain(@RequestParam("domain") String domain) {
        Tenant tenant = tenantService.findTenantByDomain(domain);
        return BaseResponse.builder().data(tenant).build();
    }

    /**
     * 根据域名获取租户ID
     *
     * @param domain
     * @return
     */
    @GetMapping("/domain/id")
    public BaseResponse findTenantIdByDomain(@RequestParam("domain") String domain) {
        Tenant tenant = tenantService.findTenantByDomain(domain);
        return BaseResponse.builder().data(tenant.getId()).build();
    }
}
