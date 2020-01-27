package com.lee.setting.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lee.enums.LineTypeEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lee.li
 * 门店每天营业额预估
 */
@Data
@TableName("work_business_unit_report")
public class BusinessUnitData {
    /**
     * 分部ID
     */
    private Long businessUnitId;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 排班日期
     */
    @TableField("report_date")
    private LocalDate reportDate;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 早餐金额(分为单位)
     */
    private Double breakfastAmount;
    /**
     * 午餐金额(分为单位)
     */
    private Double lunchAmount;
    /**
     * 晚餐第一阶段金额(分为单位)
     */
    private Double supperFirstPhaseAmount;

    /**
     * 晚餐第二阶段金额(分为单位)
     */
    private Double supperSecondPhaseAmount;

    /**
     * 晚餐第三阶段金额(分为单位)
     */
    private Double supperThirdPhaseAmount;
    /**
     * 中文描述
     */
    private String description;
    /**
     * 行类型
     */
    private LineTypeEnum lineType;
}
