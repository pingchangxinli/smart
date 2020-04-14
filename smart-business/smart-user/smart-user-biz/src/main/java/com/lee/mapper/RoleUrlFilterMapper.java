package com.lee.mapper;

import com.lee.domain.RoleUrlFilterRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态链接 & 角色 关系
 *
 * @author haitao Li
 */
@Mapper
public interface RoleUrlFilterMapper {
    /**
     * 创建动态链接 角色 关系
     * @param relation 角色 动态链接实体
     */
    void create(RoleUrlFilterRelation relation);
}
