package com.lee.type.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lee.enums.EnabledStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 排班类型
 *
 * @author lee.li
 */
@Data
@TableName(value = "work_type")
public class WorKType {
    /**
     * 主键  类型 英文简称
     */
    @TableId(type = IdType.INPUT)
    private String type;
    /**
     * 中文描述
     */
    private String description;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 启用状态
     */
    private EnabledStatus status;
}
