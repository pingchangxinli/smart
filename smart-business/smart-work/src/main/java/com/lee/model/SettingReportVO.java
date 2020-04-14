package com.lee.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author haitao Li
 * 排班报表展示
 */
@Data
public class SettingReportVO {
    /**
     * 分部ID
     */
    private Long businessUnitId;
    /**
     * 业务时段类型
     */
    private String businessPeriodType;
    /**
     * 开始时间
     */
    private LocalDate beginDate;
    /**
     * 截至时间
     */
    private LocalDate endDate;
    /**
     * 金额
     */
    private List<AmountReportVO> amountList;
    /**
     * 工时
     */
    private List<WorkTimeReportVO> workTimeList;
    /**
     * 工效
     */
    private List<WorkEfficiencyReportVO> workEfficiencyList;
}
