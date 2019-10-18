package com.lee.user.mapper;

import com.lee.user.domain.RoleUrlFilterRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态链接 & 角色 关系
 * @author lee.li
 */
@Mapper
public interface RoleUrlFilterMapper {
    /**
     * 创建动态链接 角色 关系
     * @param relation 角色 动态链接实体
     */
    void create(RoleUrlFilterRelation relation);
}
