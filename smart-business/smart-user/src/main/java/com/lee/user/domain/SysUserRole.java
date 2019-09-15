package com.lee.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户 角色 对应表
 * @author haitao.li
 */
@Data
@TableName(value="sys_user_role")
public class SysUserRole {
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value= "role_id")
    private Long roleId;
}
