package com.lee.mapper;

import com.lee.domain.UrlFilter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 动态链接配置
 * @author lee.li
 */
@Mapper
public interface UrlFilterMapper {
    /**
     * 新增动态链接配置
     * @param urlFilter
     * @return
     */
    @Insert("INSERT INTO URL_FILTER (ID,NAME,URL,ROLE_URL_FILTER_ID) VALUES (#{url.id},#{url.name},#{url.url},#{url" +
            ".roleUrlFilterId})")
    Integer create(@Param("url") UrlFilter urlFilter);
}
