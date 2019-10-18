package com.lee.user.service;

import com.lee.user.domain.RoleUrlFilterRelation;
import com.lee.user.domain.UrlFilter;
import com.lee.user.mapper.RoleUrlFilterMapper;
import com.lee.user.mapper.UrlFilterMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lee.li
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UrlFilterService {
    @Resource
    private UrlFilterMapper urlMapper;
    @Resource
    private RoleUrlFilterMapper roleUrlFilterMapper;

    /**
     * 创建链接动态配置
     * @param urlFilter
     * @return
     */
    public Boolean create(UrlFilter urlFilter) {

        RoleUrlFilterRelation relation = new RoleUrlFilterRelation();
        roleUrlFilterMapper.create(relation);

        Integer count = urlMapper.create(urlFilter);
        if (count > 1) {
            return  Boolean.TRUE;
        }
        return  Boolean.FALSE;
    }
}
