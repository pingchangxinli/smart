package com.lee.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.business.EnabledStatus;
import com.lee.common.core.Pagination;
import com.lee.tenant.domain.Shop;
import com.lee.tenant.exception.ShopExistedException;
import com.lee.tenant.exception.ShopNotExistedException;
import com.lee.tenant.mapper.ShopMapper;
import com.lee.tenant.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@Service
public class ShopServiceImpl implements ShopService {
    private static final String ERROR_NOT_EXIST = "系统中不存在该成本中心";
    private static final String ERROR_EXISTED = "系统中已存在该成本中心";
    private static final String ERROR_NEED_ID = "需要ID参数";
    @Resource
    private ShopMapper mapper;

    @Override
    public Integer createShop(Shop shop) throws ShopExistedException {
        return mapper.insert(shop);
    }

    @Override
    public Shop findShopById(Long id) {
        return mapper.selectById(id);
    }


    @Override
    public IPage<Shop> pageList(Pagination pagination, Shop shop) {
        Page<Shop> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());

        QueryWrapper queryWrapper = new QueryWrapper();

        String name = shop.getName();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        EnabledStatus status = shop.getStatus();
        if (status != null) {
            queryWrapper.eq("status", status.getValue());
        }
        IPage<Shop> iPage = mapper.selectPage(page, queryWrapper);

        return iPage;
    }

    @Override
    public Integer updateShopById(Shop shop) throws ShopNotExistedException {
        Long id = shop.getId();
        if (id == null || id.longValue() <= 0) {
            throw new IllegalArgumentException(ERROR_NEED_ID);
        }
        Shop temp = this.findShopById(id);
        if (ObjectUtils.isEmpty(temp)) {
            throw new ShopNotExistedException(ERROR_NOT_EXIST);
        }
        return mapper.updateById(shop);
    }

    @Override
    public List<Shop> findShop(Shop costCenter) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        if (costCenter != null) {
            Long id = costCenter.getId();
            if (id != null && id > 0) {
                queryWrapper.eq("id", id);
            }

            String name = costCenter.getName();
            if (StringUtils.isNotEmpty(name)) {
                queryWrapper.like("name", name);
            }

            EnabledStatus status = costCenter.getStatus();

            if (status != null) {
                queryWrapper.eq("status", status.getValue());
            }
        }
        return mapper.selectList(queryWrapper);
    }
}
