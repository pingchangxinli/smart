package com.lee.worker.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lee.li
 * 雇员展示POJO
 */
@Data
public class WorkerVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 分布ID
     */
    private Long businessUnitId;
}
