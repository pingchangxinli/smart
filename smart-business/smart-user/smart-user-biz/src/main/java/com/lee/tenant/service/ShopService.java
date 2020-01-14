package com.lee.tenant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.tenant.domain.Shop;
import com.lee.tenant.exception.ShopExistedException;
import com.lee.tenant.exception.ShopNotExistedException;

import java.util.List;

/**
 * @author lee.li
 */
public interface ShopService {
    /**
     * 新增成本中心
     *
     * @param shop 成本中心
     * @return
     */
    Integer createShop(Shop shop) throws ShopExistedException;

    /**
     * 用ID查询成本中心
     *
     * @param id id
     * @return 成本中心
     */
    Shop findShopById(Long id);


    /**
     * 根据ID更新成本中心
     *
     * @param shop 成本中心
     * @return
     * @throws ShopNotExistedException
     */
    Integer updateShopById(Shop shop) throws ShopNotExistedException;

    /**
     * 成本中心列表
     *
     * @param shop
     * @return
     */
    List<Shop> findShop(Shop shop);

    /**
     * 分页查询成本中心
     *
     * @param pagination
     * @param shop
     * @return
     */
    IPage<Shop> pageList(Pagination pagination, Shop shop);
}
