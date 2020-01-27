package com.lee.setting.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author lee.li
 * 排班报表展示
 */
@Data
public class SettingReportDTO {
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
    private List<AmountReportDTO> amountList;
    /**
     * 工时
     */
    private List<WorkTimeReportDTO> workTimeList;
    /**
     * 工效
     */
    private List<WorkEfficiencyReportDTO> workEfficiencyList;
}
