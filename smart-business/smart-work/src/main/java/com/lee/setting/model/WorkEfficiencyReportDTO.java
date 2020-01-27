package com.lee.setting.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author lee.li
 */
@Data
public class WorkEfficiencyReportDTO {
    private LocalDate reportDate;
    private String type;
    private Double amount;
}
