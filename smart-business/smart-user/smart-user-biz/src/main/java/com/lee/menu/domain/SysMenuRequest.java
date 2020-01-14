package com.lee.menu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * 菜单请求参数
 *
 * @author lee.li
 */
@Data
public class SysMenuRequest extends SysMenu {
    /**
     * 图标
     */
    private String icon;
    /**
     * 角色集合
     */
    @TableField(exist = false)
    private List<Long> roleIds;
}
