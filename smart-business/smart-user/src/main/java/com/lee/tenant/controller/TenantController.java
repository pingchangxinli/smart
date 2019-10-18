package com.lee.tenant.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.EnabledStatus;
import com.lee.common.core.exception.PageException;
import com.lee.common.core.response.BaseResponse;
import com.lee.tenant.TenantErrorEnum;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.exception.TenantExistedException;
import com.lee.tenant.exception.TenantNotExistedException;
import com.lee.tenant.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

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
     * http://127.0.0.1:8300/user-api/tenant?access_token=f09fba50-2ad2-432a-95dc-44e61160e1fa
     * {
     * "name":"1111",
     * "company_name":"sdfasdfas",
     * "domain":"a.b.com"
     * }
     *
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
        Integer count = tenantService.createTenant(tenant);
        final BaseResponse build = BaseResponse.builder().data(count > 0 ? true : false).build();
        return build;
    }

    /**
     * http://127.0.0.1:8300/user-api/tenant?access_token=be60825d-f94a-48dd-ba1d-b6dde0ff3329
     * {"id": "1","name":"name1","status":"0"}
     * @param tenant 租户信息
     * @return
     */
    @PutMapping
    public BaseResponse updateTenantById(@RequestBody Tenant tenant) {
        if (log.isDebugEnabled()) {
            log.debug("update Tenant,request tenant: {}", tenant);
        }
        Integer count = tenantService.updateTenantById(tenant);
        return BaseResponse.builder().data(count).build();
    }

    /**
     * 查询所有租户
     * http://127.0.0.1:8300/user-api/tenant/list?access_token=7bbd5508-66e6-42ee-b6e6-dade9dcad82b
     *
     * @return 租户集合
     */
    @GetMapping("/list")
    public BaseResponse list(Tenant tenant) {
        if (log.isDebugEnabled()) {
            log.debug("[Tenant contorller],tenant:{}",tenant);
        }
        List<Tenant> list = tenantService.list(tenant);
        return BaseResponse.builder().data(list).build();
    }

    /**
     * http://127.0.0.1:8300/user-api/tenant/page?access_token=be60825d-f94a-48dd-ba1d-b6dde0ff3329&page=1&limit=20
     * &name=nam
     * 分页查询
     *
     * @param page  当前页数
     * @param limit 每页条数
     * @param name  租户名称模糊查询
     * @return 分页查询结果
     */
    @GetMapping(value = "/page", params = {"page", "limit", "name"})
    public BaseResponse pageList(Integer page, Integer limit, String name) {

        IPage<Tenant> list = null;
        try {
            list = tenantService.pageList(name, page, limit);
        } catch (PageException e) {
            log.error("query tenant page failed", e);
        }

        return BaseResponse.builder().data(list).build();

    }

    /**
     * 通过ID查询租户
     * http://127.0.0.1:8300/user-api/tenant/1?access_token=7bbd5508-66e6-42ee-b6e6-dade9dcad82b
     *
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
        return BaseResponse.builder().data(tenant).build();
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
        Tenant tenant = null;
        try {
            tenant = tenantService.findTenantByDomain(domain);
        } catch (TenantNotExistedException e) {
            log.info("can not found tenant by domain:" + domain);
        }
        return BaseResponse.builder().data(tenant).build();
    }

    /**
     * http://127.0.0.1:8300/user-api/tenant/domain/id?domain=127.0.0
     * .1&access_token=f855e6e3-ec83-4014-add3-db83f07f9fcd
     * 根据域名获取租户I
     *
     * @param domain
     * @return
     */
    @GetMapping("/domain/id")
    public BaseResponse findTenantIdByDomain(@RequestParam("domain") String domain) {
        Tenant tenant = null;
        try {
            tenant = tenantService.findTenantByDomain(domain);
        } catch (TenantNotExistedException e) {
            log.info("can not found tenant id by domain:" + domain);
        }
        return BaseResponse.builder().data(tenant.getId()).build();
    }
}
