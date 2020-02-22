package com.lee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.domain.SysRoleMenu;
import com.lee.domain.SysRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lee.li
 */
public interface RoleMenuMapper extends BaseMapper<SysRoleMenu> {

    @Select("SELECT A.* FROM SYS_ROLE A,SYS_ROLE_MENU B " +
            "WHERE A.ID = B.ROLE_ID AND A.TENANT_ID = B.TENANT_ID " +
            "AND B.MENU_ID = #{menuId}")
    /**
     * 菜单ID查询角色信息
     * @param menuId 菜单ID
     * @return 角色集合
     */
    List<SysRole> selectRolesByMenuId(Long menuId);
}
