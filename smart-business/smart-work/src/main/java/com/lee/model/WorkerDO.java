package com.lee.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lee.li
 */
@Data
@TableName("work_partner")
public class WorkerDO {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 分布ID
     */
    private Long businessUnitId;
    /**
     * 生效或者失效
     */
    private EnabledStatusEnum status;

}
