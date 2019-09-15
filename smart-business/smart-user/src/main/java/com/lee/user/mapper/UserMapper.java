package com.lee.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.user.domain.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author haitao.li
 */
public interface UserMapper extends BaseMapper<SysUser> {
}
