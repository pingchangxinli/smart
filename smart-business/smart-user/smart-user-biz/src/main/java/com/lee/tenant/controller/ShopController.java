package com.lee.tenant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.EnabledStatus;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.tenant.domain.Shop;
import com.lee.tenant.exception.ShopExistedException;
import com.lee.tenant.exception.ShopNotExistedException;
import com.lee.tenant.exception.TenantNotExistedException;
import com.lee.tenant.service.ShopService;
import com.lee.tenant.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Resource
    private ShopService service;
    @Resource
    private TenantService tenantService;
    @Resource
    private HttpServletRequest request;

    /**
     * 查询成本中心列表
     *
     * @param shop
     * @return
     */
    @GetMapping
    public BaseResponse findShopByTenantId(Shop shop) {
        if (log.isDebugEnabled()) {
            log.debug("[shopController.findshop]  request params: " + shop);
        }
        List<Shop> list = service.findShop(shop);
        return BaseResponse.ok(list);
    }

    /**
     * 新增成本中心
     *
     * @param shop 成本中心
     * @return 新增条数
     */
    @PostMapping
    public BaseResponse createShop(@RequestBody Shop shop)
            throws ShopExistedException {
        shop.setStatus(EnabledStatus.ENABLED);
        int count = service.createShop(shop);
        return BaseResponse.ok(count);
    }

    /**
     * 根据ID更新成本中心
     *
     * @param shop 成本中心
     * @return 更新条数
     * @throws ShopNotExistedException 成本中心不存在
     */
    @PutMapping
    public BaseResponse updateCostCenterById(@RequestBody Shop shop) throws ShopNotExistedException {
        int count = service.updateShopById(shop);
        return BaseResponse.ok(count);
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
        Shop shop = service.findShopById(id);
        return BaseResponse.ok(shop);
    }

    /**
     * 分页查询出租户下的成本中心
     *
     * @return 该分页下的成本中心
     * @throws TenantNotExistedException 租户
     */
    @GetMapping(value = "/page")
    public BaseResponse pageList(Pagination pagination, Shop shop) {
        IPage<Shop> iPage = service.pageList(pagination, shop);
        PaginationResponse response = PaginationResponseUtil.convertIPageToPagination(iPage);
        if (log.isDebugEnabled()) {
            log.debug("[CostCenterController pageList] result:" + response);
        }
        return BaseResponse.ok(response);
    }
}
