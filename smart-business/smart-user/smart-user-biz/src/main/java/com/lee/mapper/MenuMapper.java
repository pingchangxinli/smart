package com.lee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.domain.SysMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lee.li
 */
public interface MenuMapper extends BaseMapper<SysMenu> {
    @Select("SELECT A.* FROM SYS_MENU A,SYS_ROLE_MENU B " +
                "WHERE A.ID = B.MENU_ID AND A.TENANT_ID = B.TENANT_ID " +
                    "AND B.ROLE_ID = #{roleId}")
    List<SysMenu> selectMenusByRoleId(Long roleId);
}
