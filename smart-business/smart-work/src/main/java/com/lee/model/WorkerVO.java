package com.lee.model;

import com.lee.api.vo.BusinessUnitVO;
import com.lee.enums.EnabledStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author haitao Li
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
     * 分部信息
     */
    private BusinessUnitVO businessUnit;
    /**
     * 生效或者失效
     */
    private EnabledStatusEnum status;
}
