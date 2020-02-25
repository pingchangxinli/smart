package com.lee.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author lee.li
 */
@Data
public class WorkEfficiencyReportVO {
    private LocalDate reportDate;
    private String type;
    private Double amount;
}
