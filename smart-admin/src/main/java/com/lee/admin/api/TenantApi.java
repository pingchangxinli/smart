package com.lee.admin.api;

import com.lee.admin.domain.Tenant;
import com.lee.admin.response.AdminResponse;
import com.lee.admin.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haitao.li
 */
@RestController
@RequestMapping("/tenant")
public class TenantApi {
    @Autowired
    private TenantService tenantService;
    @RequestMapping("/list")
    public AdminResponse list() {
        AdminResponse adminResponse = new AdminResponse();
        List<Tenant> list = tenantService.list();
        return adminResponse;
    }
}
