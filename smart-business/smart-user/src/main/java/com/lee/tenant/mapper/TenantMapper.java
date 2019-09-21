package com.lee.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.tenant.domain.Tenant;
import org.apache.ibatis.annotations.Param;

/**
 * @author haitao.li
 */
public interface TenantMapper extends BaseMapper<Tenant> {
}
