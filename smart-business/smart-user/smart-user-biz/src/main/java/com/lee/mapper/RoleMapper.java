package com.lee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.domain.SysRoleDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 获取用户角色
 *
 * @author lee.li
 */
public interface RoleMapper extends BaseMapper<SysRoleDO> {
    /**
     * 联表查询,得到角色信息
     *
     * @param userId
     * @return
     */
    @Select("SELECT A.* FROM SYS_ROLE A,SYS_USER_ROLE B WHERE A.ID = B.ROLE_ID AND B.USER_ID = #{user_id}")
    List<SysRoleDO> selectRoleList(@Param("user_id") Long userId);
}
