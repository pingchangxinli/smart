package com.lee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lee.common.core.BaseObject;
import com.lee.enums.EnabledStatusEnum;
import lombok.Data;


/**
 * @author lee.li
 */
@Data
@TableName("sys_menu")
public class SysMenu extends BaseObject {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 界面显示值
     */
    private String name;
    /**
     * 父节点ID
     */
    @TableField("parent_id")
    @JsonProperty("parent_id")
    private Long parentId;
    /**
     * 路径URL
     */
    private String path;
    /**
     * 节点在父节点下的顺序
     */
    private Integer sort;
    /**
     * 权限
     */
    @TableField(exist = false)
    private String permissions;

    /**
     * 是否启用
     */
    private EnabledStatusEnum status;
}
