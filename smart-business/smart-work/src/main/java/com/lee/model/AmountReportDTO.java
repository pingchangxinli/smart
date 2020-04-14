package com.lee.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author haitao Li
 */
@Data
public class AmountReportDTO {
    private LocalDate reportDate;
    private String type;
    private String typeDesc;
    private Double amount;
}
