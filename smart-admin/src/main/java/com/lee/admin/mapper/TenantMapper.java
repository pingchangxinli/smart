package com.lee.admin.mapper;

import com.lee.admin.domain.Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author haitao.li
 */
@Mapper
public interface TenantMapper {
    @Select("SELECT * FROM TENANT")
    List<Tenant> list();
}
