package com.lee.setting.model;

import com.lee.enums.LineTypeEnum;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author lee.li
 * 门店每天营业额预估
 */
@Data
public class BusinessUnitDataVO {
    /**
     * 门店ID
     */
    private Long businessUnitId;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 排班日期
     */
    private LocalDate reportDate;
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
