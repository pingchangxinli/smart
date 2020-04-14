package com.lee.model;

import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 排班类型
 *
 * @author haitao Li
 */
@Data
public class WorKTypeVO {
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
    private EnabledStatusEnum status;
}
